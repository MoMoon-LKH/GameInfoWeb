package com.project.gameInfo.service;

import com.project.gameInfo.controller.dto.PlatformDto;
import com.project.gameInfo.domain.GamesPlatform;
import com.project.gameInfo.domain.Platform;
import com.project.gameInfo.repository.GamesPlatformRepository;
import com.project.gameInfo.repository.PlatformRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlatformService {

    private final PlatformRepository platformRepository;

    @Transactional
    public Long save(String name) {
        return platformRepository.save(new Platform(name)).getId();
    }

    @Transactional
    public boolean deleteAll(List<Platform> platforms) {
        try {
            platformRepository.deleteAll(platforms);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public void updatePlatform(Platform platform, String name) {
        platform.updateName(name);
    }

    public Platform findById(Long id) {
        return platformRepository.findById(id).orElseThrow(NoSuchFieldError::new);
    }

    public Optional<Platform> findByName(String name) {
        return platformRepository.findByName(name);
    }

    public List<Platform> findAllByGamesId(Long id) {
        return platformRepository.findAllByGamesId(id);
    }

    public List<Platform> findAllByIds(List<Long> ids) {
        return platformRepository.findAllByIds(ids);
    }

    public List<PlatformDto> findAllByPage(Pageable pageable) {
        return platformRepository.findAllByPage(pageable);
    }

    public List<PlatformDto> findAllBySearch(String search, Pageable pageable) {
        return platformRepository.findAllBySearch(search, pageable);
    }
}
