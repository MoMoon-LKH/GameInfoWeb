package com.project.gameInfo.service;

import com.project.gameInfo.domain.ReviewScore;
import com.project.gameInfo.repository.ReviewScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewScoreService {

    private final ReviewScoreRepository reviewScoreRepository;

    @Transactional
    public Long save(ReviewScore reviewScore) {
        return reviewScoreRepository.save(reviewScore).getId();
    }


    public Double findScoreByGamesId(Long id) {
        return reviewScoreRepository.findAvgByGamesId(id) == null ? 0.0 : reviewScoreRepository.findAvgByGamesId(id);
    }
}
