package com.project.gameInfo.repository;

import com.project.gameInfo.controller.dto.PlatformDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomPlatformRepository {

    List<PlatformDto> findAllByPage(Pageable pageable);

    List<PlatformDto> findAllBySearch(String search, Pageable pageable);
}
