package com.project.gameInfo.repository;

import com.project.gameInfo.domain.GamesGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GamesGenreRepository extends JpaRepository<GamesGenre, Long> {

    @Query("select function('group_concat', g.name) from games_genre gg " +
            "join fetch Genre g on g.id = gg.genre.id " +
            "join fetch Games ga on ga.id = gg.games.id " +
            "group by ga.id " +
            "having ga.id = :id"
            )
    String findGenresByGamesId(@Param("id") Long id);
}
