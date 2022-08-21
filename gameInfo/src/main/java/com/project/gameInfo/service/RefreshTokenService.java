package com.project.gameInfo.service;

import com.project.gameInfo.domain.RefreshToken;
import com.project.gameInfo.exception.RefreshTokenSecurityException;
import com.project.gameInfo.exception.NotFoundTokenException;
import com.project.gameInfo.jwt.TokenProvider;
import com.project.gameInfo.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
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

    @Transactional
    public void accessUpdate(RefreshToken refreshToken, String access){
        refreshToken.updateAccessToken(access);
    }

    public Optional<RefreshToken> findByMemberId(Long id) {
        return refreshTokenRepository.findByMember_Id(id);
    }

    public RefreshToken findByRefreshToken(String refresh){

        return refreshTokenRepository.findByRefreshToken(refresh).orElseThrow(() -> new NotFoundTokenException("Refresh Token Not Found"));
    }
    @Transactional
    public String generateAccessTokenFromRefreshToken(String refresh, String access) {

        String accessToken;

        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(refresh)
                .orElseThrow(() -> new NotFoundTokenException("Refresh Token Not Found"));

        if (refreshToken.getAccessToken().equals(access)) {

            try {
                Authentication authentication = tokenProvider.getRefreshAuthentication(refresh);
                accessToken = tokenProvider.createAccessToken(authentication);
                refreshToken.updateAccessToken(accessToken);

            } catch (Exception e) {
                throw new BadCredentialsException("해당 사용자 정보가 없습니다.");
            }

            return accessToken;

        } else{

            refreshTokenRepository.delete(refreshToken);

            throw new RefreshTokenSecurityException();
        }

    }
}
