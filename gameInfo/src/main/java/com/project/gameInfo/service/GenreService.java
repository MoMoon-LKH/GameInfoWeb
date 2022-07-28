package com.project.gameInfo.service;


import com.project.gameInfo.controller.dto.GenreDto;
import com.project.gameInfo.domain.Genre;
import com.project.gameInfo.repository.CustomGenreRepositoryImpl;
import com.project.gameInfo.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final CustomGenreRepositoryImpl genreCustomRepository;


    @Transactional
    public Long save(Genre genre) {
        return genreRepository.save(genre).getId();
    }

    @Transactional
    public void updateGenre(Genre genre, String name) {
        genre.updateName(name);
    }

    public Genre findById(Long id) {
        return genreRepository.findById(id).orElseThrow(NoSuchFieldError::new);
    }

    public List<GenreDto> getListByPage(Pageable pageable) {
        return genreRepository.findAllByPage(pageable);
    }

    public List<Genre> findAllByGamesId(Long id) {
        return genreRepository.findAllByGamesId(id);
    }
}
