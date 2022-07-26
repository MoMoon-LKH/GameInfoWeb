package com.project.gameInfo.repository;

import com.project.gameInfo.controller.dto.GenreDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomGenreRepository {
    List<GenreDto> findAllByPage(Pageable pageable);
}
