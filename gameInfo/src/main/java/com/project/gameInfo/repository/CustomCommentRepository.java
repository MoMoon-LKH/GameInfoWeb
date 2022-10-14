package com.project.gameInfo.repository;

import com.project.gameInfo.controller.dto.CommentDto;
import com.project.gameInfo.domain.Comment;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomCommentRepository {

    List<Comment> findAllByPostId(Long postId, Pageable pageable);

    Integer maxGroupNum(Long postId);
    Integer maxGroupOrder(Long postId, int groupNum);

}
