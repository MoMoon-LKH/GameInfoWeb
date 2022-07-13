package com.project.gameInfo.repository;

import com.project.gameInfo.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByRefreshToken(@Param("refresh_token") String refreshToken);

    Optional<RefreshToken> findByMember_Id(@Param("id") Long id);
}
