package com.project.gameInfo.repository;

import com.project.gameInfo.controller.dto.PostListDto;
import com.project.gameInfo.domain.QPost;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomPostRepositoryImpl implements CustomPostRepository{

    private final JPAQueryFactory queryFactory;
    QPost post = QPost.post;


    @Override
    public List<PostListDto> findAllByCategoryIdAndGamePage(Long categoryId, Long gameId, Pageable pageable) {

        return queryFactory.select(
                Projections.bean(PostListDto.class,
                        post.id,
                        post.title,
                        post.view,
                        post.member.id.as("memberId"),
                        post.member.nickname.as("nickname"),
                        post.comments.size().as("commentCount"),
                        post.createDate
                ))
                .from(post)
                .where(
                        post.category.id.eq(categoryId),
                        eqGameId(gameId)
                        )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.createDate.desc())
                .fetch();
    }


    @Override
    public List<PostListDto> findAllByCategoryId(Long categoryId, Pageable pageable) {

        return queryFactory.select(
                        Projections.bean(PostListDto.class,
                                post.id,
                                post.title,
                                post.view,
                                post.member.id.as("memberId"),
                                post.member.nickname.as("nickname"),
                                post.comments.size().as("commentCount"),
                                post.createDate
                        ))
                .from(post)
                .where(post.category.id.eq(categoryId))
                .limit(pageable.getPageSize())
                .orderBy(post.createDate.desc())
                .fetch();
    }

    @Override
    public Long countByCategoryAndGamePage(Long categoryId, Long gamesId) {
        return queryFactory.select(
                        post.id
                ).from(post)
                .where(post.category.id.eq(categoryId),
                        eqGameId(gamesId)).stream().count();
    }

    private BooleanExpression eqGameId(Long gameId) {
        if (gameId != null) {
            return post.games.id.eq(gameId);
        }

        return null;
    }

}
