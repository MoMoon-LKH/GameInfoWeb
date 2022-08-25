package com.project.gameInfo.repository;

import com.project.gameInfo.domain.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlatformRepository extends JpaRepository<Platform, Long>, CustomPlatformRepository{

    Optional<Platform> findByName(String name);

    @Query("select p.id, p.name from Platform p " +
            "join fetch games_platform " +
            "join fetch Games g " +
            "where g.id = :id")
    List<Platform> findAllByGamesId(@Param("id") Long id);


    @Query("select p from Platform p where p.id in :ids")
    List<Platform> findAllByIds(@Param("ids") List<Long> ids);

}
