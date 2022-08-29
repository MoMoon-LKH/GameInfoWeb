package com.project.gameInfo.repository;

import com.project.gameInfo.controller.dto.GenreDto;
import com.project.gameInfo.domain.Genre;
import com.project.gameInfo.domain.QGames;
import com.project.gameInfo.domain.QGamesGenre;
import com.project.gameInfo.domain.QGenre;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomGenreRepositoryImpl implements CustomGenreRepository{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<GenreDto> findAllByPage(Pageable pageable){
        QGenre genre = QGenre.genre;
        return jpaQueryFactory.select(Projections.bean(GenreDto.class,
                        genre.id.as("id"),
                        genre.name.as("name")))
                .from(genre)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(genre.name.asc())
                .fetch();

    }

    @Override
    public List<GenreDto> findAllDto() {
        QGenre genre = QGenre.genre;
        return jpaQueryFactory.select(Projections.bean(GenreDto.class,
                genre.id.as("id"),
                genre.name.as("name")))
                .from(genre)
                .orderBy(genre.name.asc())
                .fetch();
    }

    @Override
    public List<Genre> findAllByGamesId(Long id) {
        QGenre genre = QGenre.genre;
        QGamesGenre gamesGenre = QGamesGenre.gamesGenre;

        return jpaQueryFactory.selectFrom(genre)
                .join(gamesGenre)
                .where(gamesGenre.games.id.eq(id))
                .fetch();
    }
}
