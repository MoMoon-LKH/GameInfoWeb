package com.project.gameInfo.service;

import com.project.gameInfo.domain.Games;
import com.project.gameInfo.domain.GamesPlatform;
import com.project.gameInfo.domain.Platform;
import com.project.gameInfo.repository.GamesPlatformRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GamesPlatformService {

    private final GamesPlatformRepository gamesPlatformRepository;

    @Transactional
    public Long save(Games games, Platform platform) {
        return gamesPlatformRepository.save(new GamesPlatform(games, platform)).getId();
    }

    public String findPlatformsByGamesId(Long id) {
        try {
            return gamesPlatformRepository.findPlatformsByGamesId(id).replace(",", " ");
        } catch (Exception e) {
            return " ";
        }
    }

}
