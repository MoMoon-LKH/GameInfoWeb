package com.project.gameInfo.repository;

import com.project.gameInfo.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, CustomCommentRepository {


    List<Comment> findAllByPostId(@Param("postId") Long postId);
}
