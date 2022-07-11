package com.project.gameInfo.service;

import com.project.gameInfo.domain.RefreshToken;
import com.project.gameInfo.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Long save(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken).getId();
    }

}
