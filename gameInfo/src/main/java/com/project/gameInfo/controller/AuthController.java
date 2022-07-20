package com.project.gameInfo.controller;

import com.project.gameInfo.controller.dto.LoginDto;
import com.project.gameInfo.controller.dto.TokenDto;
import com.project.gameInfo.domain.Member;
import com.project.gameInfo.domain.RefreshToken;
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
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenProvider tokenProvider;

    private final MemberService memberService;

    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    @PostMapping("/login")
    public ResponseEntity<?> authorize(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getMemberId(), loginDto.getPassword());

        Authentication authentication =
                authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String access = tokenProvider.createAccessToken(authentication);
        String refresh = tokenProvider.createRefreshToken(authentication);

        Member member = memberService.findMemberByMemberId(loginDto.getMemberId());
        Optional<RefreshToken> refreshToken = refreshTokenService.findByMemberId(member.getId());


        if (refreshToken.isPresent()) {
            refreshTokenService.update(refreshToken.get(), refresh);
        }else{
            RefreshToken newRefresh = RefreshToken.createRefreshToken(refresh, member);
            refreshTokenService.save(newRefresh);
        }

        response.setHeader("Authorization", "Bearer " + access);

        // Refresh Token Cookie 로 저장
        // 보안 공격을 고려하여 HttpOnly, Secure 속성을 true
        Cookie cookie = new Cookie("gameInfo", refresh);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        Map<String, String> map = new HashMap<>();
        map.put("message", "Login Success");
        map.put("id", member.getMemberId());
        map.put("nickname", member.getNickname());


        return ResponseEntity.ok(map);
    }

    @PostMapping("/re-access")
    public ResponseEntity<?> reAuthorize(HttpServletResponse response, @CookieValue(value = "gameInfo") Cookie cookie) {

        if(cookie != null) {
            String accessToken = refreshTokenService.generateAccessTokenFromRefreshToken(cookie.getValue(), response.getHeader("Authorization").replace("Bearer " ,""));
            response.setHeader("Authorization", "Bearer " + accessToken);

            return ResponseEntity.ok("AccessToken refresh");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("RefreshToken Not Found");
    }



}
