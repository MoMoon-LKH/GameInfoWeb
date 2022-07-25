package com.project.gameInfo.service;

import com.project.gameInfo.controller.dto.GamesDto;
import com.project.gameInfo.domain.Games;
import com.project.gameInfo.repository.GamesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GamesService {

    private final GamesRepository gamesRepository;

    @Transactional
    public Long save(Games games) {
        return gamesRepository.save(games).getId();
    }


}
