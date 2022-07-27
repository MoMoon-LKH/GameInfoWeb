package com.project.gameInfo.repository;

import com.project.gameInfo.domain.GamesGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamesGenreRepository extends JpaRepository<GamesGenre, Long> {

}
