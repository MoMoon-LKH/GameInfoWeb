package com.project.gameInfo.repository;

import com.project.gameInfo.controller.dto.PostListDto;
import com.project.gameInfo.domain.QPost;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomPostRepositoryImpl implements CustomPostRepository{

    private final JPAQueryFactory queryFactory;


    @Override
    public List<PostListDto> findAllByCategoryIdAndGamePage(Long categoryId, Long gameId, Pageable pageable) {
        QPost post = QPost.post;

        return queryFactory.select(
                Projections.bean(PostListDto.class,
                        post.id,
                        post.title,
                        post.view,
                        post.member.id.as("memberId"),
                        post.member.nickname,
                        post.comments.size().as("commentCount"),
                        post.createDate
                ))
                .from(post)
                .where(post.games.id.eq(gameId).and(post.category.id.eq(categoryId)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.createDate.desc())
                .fetch();
    }
}
