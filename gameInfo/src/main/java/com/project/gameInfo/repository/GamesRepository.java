package com.project.gameInfo.repository;

import com.project.gameInfo.domain.Games;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GamesRepository extends JpaRepository<Games, Long>, CustomGamesRepository {

    List<Games> findAllBy(Pageable pageable);
}
