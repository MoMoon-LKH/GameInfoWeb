package com.project.gameInfo.service;

import com.project.gameInfo.domain.Platform;
import com.project.gameInfo.repository.PlatformRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlatformService {

    private final PlatformRepository platformRepository;


    @Transactional
    public Long save(String name) {
        return platformRepository.save(new Platform(name)).getId();
    }

    public List<Platform> findAllByGamesId(Long id) {
        return platformRepository.findAllByGamesId(id);
    }

}
