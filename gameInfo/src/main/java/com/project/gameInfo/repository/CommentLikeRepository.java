package com.project.gameInfo.repository;

import com.project.gameInfo.domain.CommentLike;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByCommentIdAndMemberId(@Param("commentId") Long commentId, @Param("memberId") Long memberId);
}
