package com.project.gameInfo.service;

import com.project.gameInfo.domain.RefreshToken;
import com.project.gameInfo.exception.NotFindMemberException;
import com.project.gameInfo.exception.RefreshTokenSecurityException;
import com.project.gameInfo.exception.TokenNotFoundException;
import com.project.gameInfo.jwt.TokenProvider;
import com.project.gameInfo.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final TokenProvider tokenProvider;

    @Transactional
    public Long save(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken).getId();
    }

    @Transactional
    public void delete(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }

    @Transactional
    public void update(RefreshToken refreshToken, String refresh, String access) {
        refreshToken.update(refresh, access);
    }

    public Optional<RefreshToken> findByMemberId(Long id) {
        return refreshTokenRepository.findByMember_Id(id);
    }

    @Transactional
    public String generateAccessTokenFromRefreshToken(String refresh, String access) {

        String accessToken;

        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(refresh)
                .orElseThrow(() -> new TokenNotFoundException("Refresh Token Not Found"));

        if (refreshToken.getAccessToken().equals(access)) {

            try {
                Authentication authentication = tokenProvider.getRefreshAuthentication(refresh);
                System.out.println("authentication = " + authentication);
                accessToken = tokenProvider.createAccessToken(authentication);

            } catch (Exception e) {
                throw new BadCredentialsException("해당 사용자 정보가 없습니다.");
            }

            refreshToken.updateAccessToken(accessToken);
            return accessToken;

        } else{

            refreshTokenRepository.delete(refreshToken);

            throw new RefreshTokenSecurityException();
        }

    }
}
