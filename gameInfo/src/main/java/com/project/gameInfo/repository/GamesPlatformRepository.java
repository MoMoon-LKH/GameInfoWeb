package com.project.gameInfo.repository;

import com.project.gameInfo.domain.GamesPlatform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GamesPlatformRepository extends JpaRepository<GamesPlatform, Long> {

    @Query("select function('group_concat', p.name) from games_platform gp " +
            "join fetch Platform p on p.id = gp.platform.id " +
            "join fetch Games ga on ga.id = gp.games.id " +
            "group by ga.id " +
            "having ga.id = :id"
    )
    String findPlatformsByGamesId(@Param("id") Long id);
}
