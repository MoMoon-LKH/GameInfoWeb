package com.project.gameInfo.service;

import com.project.gameInfo.controller.dto.GameSearchDto;
import com.project.gameInfo.controller.dto.GamesDto;
import com.project.gameInfo.domain.Games;
import com.project.gameInfo.exception.NotFoundGameException;
import com.project.gameInfo.repository.GamesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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


    public List<GamesDto> findAllByPage(Pageable pageable) {

        return gamesRepository.findAllByPage(pageable);
    }

    public List<GamesDto> findAllBySearch(String search, Pageable pageable) {
        return gamesRepository.findAllBySearch(search, pageable);
    }

    public List<GamesDto> findAllBySearchColumn(String search, String column, Pageable pageable) {
        return gamesRepository.findAllBySearchColumn(search, column, pageable);
    }

    public Games findById(Long id) {
        return gamesRepository.findById(id).orElseThrow(NotFoundGameException::new);
    }

    public GamesDto findDtoById(Long id) {
        return gamesRepository.findDtoById(id);
    }

    public Long countAll() {
        return gamesRepository.countAllBy();
    }

    public Long countAllByName(String name) {
        return gamesRepository.countAllByNameStartsWith(name);
    }
}
