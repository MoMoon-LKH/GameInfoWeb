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

    @Transactional
    public boolean deleteAll(List<Genre> genres) {
        try {
            genreRepository.deleteAll(genres);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Genre findById(Long id) {
        return genreRepository.findById(id).orElseThrow(NoSuchFieldError::new);
    }

    public List<GenreDto> findAll() {
        return genreRepository.findAllDto();
    }

    public List<GenreDto> getListByPage(Pageable pageable) {
        return genreRepository.findAllByPage(pageable);
    }

    public List<Genre> findAllByName(String search, Pageable pageable) {
        return genreRepository.findAllByNameStartingWithOrderByNameAsc(search, pageable);
    }

    public List<Genre> findAllByIds(List<Long> ids) {
        return genreRepository.findByIds(ids);
    }

    public List<Genre> findAllByGamesId(Long id) {
        return genreRepository.findAllByGamesId(id);
    }
}
