package com.project.gameInfo.repository;

import com.project.gameInfo.controller.dto.GameSearchDto;
import com.project.gameInfo.controller.dto.GamesDto;
import com.project.gameInfo.domain.*;
import com.querydsl.core.BooleanBuilder;
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


        return queryFactory
                .select(Projections.bean(GamesDto.class,
                        games.id,
                        games.name,
                        games.introduction,
                        games.company,
                        games.releaseDate,
                        games.imageUrl.as("imgUrl")
                ))
                .from(games)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }


    @Override
    public GamesDto findDtoById(Long id) {
        QGames games = QGames.games;
        QReviewScore reviewScore = QReviewScore.reviewScore;

        return queryFactory
                .select(Projections.bean(GamesDto.class,
                        games.id,
                        games.name,
                        games.introduction,
                        games.company,
                        games.releaseDate,
                        games.imageUrl.as("imgUrl")
                ))
                .from(games)
                .where(games.id.eq(id))
                .fetchFirst();

    }

    @Override
    public List<GamesDto> findAllBySearch(String search, Pageable pageable) {
        QGames games = QGames.games;
        QReviewScore reviewScore = QReviewScore.reviewScore;


        return queryFactory
                .select(Projections.bean(GamesDto.class,
                        games.id,
                        games.name,
                        games.introduction,
                        games.company,
                        games.releaseDate,
                        games.imageUrl.as("imgUrl")

                ))
                .from(games)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .where(games.name.startsWith(search))
                .fetch();
    }


    @Override
    public List<GamesDto> findAllBySearchColumn(String search, String column, Pageable pageable) {

        QGames games = QGames.games;

        BooleanBuilder builder = new BooleanBuilder();

        if (column.equals("name")) {
            builder.and(games.name.startsWith(search));
        }

        if (column.equals("company")) {
            builder.and(games.company.startsWith(search));
        }

        return queryFactory.select(Projections.bean(GamesDto.class,
                        games.id,
                        games.name,
                        games.introduction,
                        games.company,
                        games.releaseDate,
                        games.imageUrl.as("imgUrl")))
                .from(games)
                .where(builder)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }
}
