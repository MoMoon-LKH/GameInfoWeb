package com.project.gameInfo.repository;

import com.project.gameInfo.domain.CommentDislike;
import com.project.gameInfo.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentDislikeRepository extends JpaRepository<CommentDislike, Long> {


    Optional<CommentDislike> findByCommentIdAndMemberId(@Param("commentId") Long commentId, @Param("memberId") Long memberId);


}
