package com.project.gameInfo.repository;

import com.project.gameInfo.controller.dto.GamesDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomGamesRepository {

    List<GamesDto> findAllByPage(Pageable pageable);
}
