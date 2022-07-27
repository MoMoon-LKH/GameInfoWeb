package com.project.gameInfo.repository;

import com.project.gameInfo.controller.dto.GamesDto;
import com.project.gameInfo.domain.QGames;
import com.project.gameInfo.domain.QGenre;
import com.project.gameInfo.domain.QReviewScore;
import com.project.gameInfo.domain.ReviewScore;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.jpa.repository.support.QueryHints.from;

@Repository
@RequiredArgsConstructor
public class CustomGamesRepositoryImpl implements CustomGamesRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<GamesDto> findAllByPage(Pageable pageable) {
        QGames games = QGames.games;
        QReviewScore reviewScore = QReviewScore.reviewScore;
        QGenre qGenre = QGenre.genre;


        return queryFactory
                .select(Projections.constructor(GamesDto.class,
                        games.id,
                        games.name,
                        games.introduction,
                        games.company,
                        games.releaseDate,
                        reviewScore.score.avg(),
                        null,
                        null,
                        null
                ))
                .from(games)
                .innerJoin(reviewScore)
                .fetch();
    }
}
