package com.project.gameInfo.repository;

import com.project.gameInfo.domain.GamesPlatform;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamesPlatformRepository extends JpaRepository<GamesPlatform, Long> {
}
