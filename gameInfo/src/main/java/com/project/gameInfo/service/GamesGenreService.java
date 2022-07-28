package com.project.gameInfo.service;

import com.project.gameInfo.domain.Games;
import com.project.gameInfo.domain.GamesGenre;
import com.project.gameInfo.domain.Genre;
import com.project.gameInfo.repository.GamesGenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GamesGenreService {

    private final GamesGenreRepository gamesGenreRepository;

    @Transactional
    public Long save(Games games, Genre genre) {

        return gamesGenreRepository.save(new GamesGenre(games, genre)).getId();
    }

    public String findGenresByGamesId(Long id) {
        return gamesGenreRepository.findGenresByGamesId(id).replace(",", " ");
    }

}
