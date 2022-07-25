package com.project.gameInfo.repository;

import com.project.gameInfo.domain.Games;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamesRepository extends JpaRepository<Games, Long> {

}
