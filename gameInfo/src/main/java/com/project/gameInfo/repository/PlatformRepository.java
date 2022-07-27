package com.project.gameInfo.repository;

import com.project.gameInfo.domain.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlatformRepository extends JpaRepository<Platform, Long> {


    @Query("select p.id, p.name from Platform p " +
            "join fetch games_platform " +
            "join fetch Games g " +
            "where g.id = :id")
    List<Platform> findAllByGamesId(@Param("id") Long id);
}
