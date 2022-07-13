package com.project.gameInfo.service;

import com.project.gameInfo.controller.dto.CommentDto;
import com.project.gameInfo.domain.Comment;
import com.project.gameInfo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;


    @Transactional
    public Long save(Comment comment) {
        return commentRepository.save(comment).getId();
    }

    @Transactional
    public void updateComment(Comment comment, CommentDto commentDto) {
        comment.updateComment(commentDto);
    }


    @Transactional
    public void deleteComment(Comment comment) {
        comment.deleteComment();
    }
}
