package com.project.gameInfo.controller;

import com.project.gameInfo.controller.dto.LoginDto;
import com.project.gameInfo.controller.dto.TokenDto;
import com.project.gameInfo.domain.Member;
import com.project.gameInfo.domain.RefreshToken;
import com.project.gameInfo.domain.enums.MemberStatus;
import com.project.gameInfo.jwt.TokenProvider;
import com.project.gameInfo.service.MemberService;
import com.project.gameInfo.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final TokenProvider tokenProvider;

    private final MemberService memberService;

    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    @PostMapping("/auth/login")
    public ResponseEntity<?> authorize(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response) {
        
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getMemberId(), loginDto.getPassword());

        Authentication authentication =
                authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // CustomUserDetailService.loadUserByUsername 실행

        String access = tokenProvider.createAccessToken(authentication);
        String refresh = tokenProvider.createRefreshToken(authentication);

        Member member = memberService.findMemberByMemberId(loginDto.getMemberId());
        Optional<RefreshToken> refreshToken = refreshTokenService.findByMemberId(member.getId());


        if (refreshToken.isPresent()) {
            refreshTokenService.update(refreshToken.get(), refresh, access);
        }else{
            RefreshToken newRefresh = RefreshToken.createRefreshToken(refresh, access, member);
            refreshTokenService.save(newRefresh);
        }

        response.setHeader("Authorization", "Bearer " + access);

        // Refresh Token Cookie 로 저장
        // 보안 공격을 고려하여 HttpOnly, Secure 속성을 true
        Cookie cookie = new Cookie("gameInfo", refresh);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(cookie);

        Map<String, Object> map = new HashMap<>();
        map.put("message", "Login Success");
        map.put("id", member.getId());
        map.put("nickname", member.getNickname());
        map.put("accessToken", access);


        return ResponseEntity.ok(map);
    }

    @PostMapping("/auth/re-access") // 보안화된 cookie를 쓰기위해서는 https 설정이 필요함
    public ResponseEntity<?> reAuthorize(@RequestParam String access,
            HttpServletRequest request, HttpServletResponse response, @CookieValue(value = "gameInfo") Cookie cookie) {


        if(access != null) {

            String accessToken = refreshTokenService.generateAccessTokenFromRefreshToken(cookie.getValue(), access);
            response.setHeader("Authorization", "Bearer " + accessToken);

            return ResponseEntity.ok("AccessToken refresh");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("RefreshToken Not Found");
    }


    @PostMapping("/user/logout")
    public ResponseEntity<?> logout(
            @AuthenticationPrincipal User user,
            HttpServletResponse response) {

        Member member = memberService.findMemberByMemberId(user.getUsername());
        Optional<RefreshToken> refreshToken = refreshTokenService.findByMemberId(member.getId());

        refreshToken.ifPresent(refreshTokenService::delete);

        Cookie delete = new Cookie("gameInfo", null);
        delete.setSecure(true);
        delete.setHttpOnly(true);
        delete.setPath("/");
        delete.setMaxAge(0);

        response.addCookie(delete);

        return ResponseEntity.ok("로그아웃이 성공적으로 이루어졌습니다.");

    }

    @GetMapping("/user/is-manage")
    public ResponseEntity<?> getAuthority(@AuthenticationPrincipal User user){

        Member member = memberService.findMemberByMemberId(user.getUsername());
        
        if(member.getRoles().equals("MANAGE") || member.getRoles().equals("ADMIN"))
            return ResponseEntity.ok(true);

        return ResponseEntity.ok(false);
    }



}
