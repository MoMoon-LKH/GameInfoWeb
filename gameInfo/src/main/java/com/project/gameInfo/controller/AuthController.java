package com.project.gameInfo.controller;

import com.project.gameInfo.controller.dto.LoginDto;
import com.project.gameInfo.controller.dto.TokenDto;
import com.project.gameInfo.domain.Member;
import com.project.gameInfo.domain.RefreshToken;
import com.project.gameInfo.jwt.TokenProvider;
import com.project.gameInfo.service.MemberService;
import com.project.gameInfo.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenProvider tokenProvider;

    private final MemberService memberService;

    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    @PostMapping("/login")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getMemberId(), loginDto.getPassword());

        Authentication authentication =
                authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String access = tokenProvider.createAccessToken(authentication);
        String refresh = tokenProvider.createRefreshToken(authentication);

        Member member = memberService.findMemberByMemberId(loginDto.getMemberId());

        RefreshToken refreshToken = RefreshToken.createRefreshToken(refresh, access, member);

        refreshTokenService.save(refreshToken);

        return ResponseEntity.ok(new TokenDto(access, refresh));
    }

    @PostMapping("/re-access")
    public ResponseEntity<?> reAuthorize(@Valid @RequestBody TokenDto tokenDto, @RequestParam String memberId) {

        String accessToken = refreshTokenService.generateAccessTokenFromRefreshToken(tokenDto.getRefreshToken(), memberId);

        Map<String, String> map = new HashMap<>();
        map.put("access", accessToken);

        return ResponseEntity.ok(map);
    }



}
