package com.project.gameInfo.repository;

import com.project.gameInfo.controller.dto.CommentDto;
import com.project.gameInfo.domain.Comment;
import com.project.gameInfo.domain.QComment;
import com.project.gameInfo.domain.QMember;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomCommentRepositoryImpl implements CustomCommentRepository{

    private final JPAQueryFactory factory;

    @Override
    public List<Comment> findAllByPostId(Long postId, Pageable pageable) {

        QComment comment = QComment.comment;

        return factory.select(
                    comment
                )
                .from(comment)
                .where(comment.post.id.eq(postId))
                .orderBy(
                        comment.groupNum.asc(),
                        comment.groupOrder.asc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

    }

    @Override
    public Integer maxGroupOrder(Long postId, int groupNum) {

        QComment comment = QComment.comment;

        return factory.select(
                comment.groupOrder.max()
        ).from(comment)
                .where(
                        comment.post.id.eq(postId),
                        comment.groupNum.eq(groupNum)
                        )
                .fetchOne();
    }

    @Override
    public Integer maxGroupNum(Long postId) {

        QComment comment = QComment.comment;

        return factory.select(
                        comment.groupNum.max().coalesce(-1)
                ).from(comment)
                .where(comment.post.id.eq(postId))
                .fetchOne();
    }
}
