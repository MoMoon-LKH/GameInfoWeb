package com.project.gameInfo.repository;

import com.project.gameInfo.controller.dto.GenreDto;
import com.project.gameInfo.domain.Genre;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CustomGenreRepository {
    List<GenreDto> findAllByPage(Pageable pageable);

    List<GenreDto> findAllDto();

    List<Genre> findAllByGamesId(Long id);
}
