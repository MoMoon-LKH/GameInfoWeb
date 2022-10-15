package com.project.gameInfo.service;

import com.project.gameInfo.controller.dto.CommentDto;
import com.project.gameInfo.controller.dto.UpdateCommentDto;
import com.project.gameInfo.domain.Comment;
import com.project.gameInfo.exception.NotFoundCommentException;
import com.project.gameInfo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void updateComment(Comment comment, UpdateCommentDto commentDto) {
        comment.updateComment(commentDto);
    }


    @Transactional
    public void deleteComment(Comment comment) {
        comment.deleteComment();
    }

    @Transactional
    public void deletePostComment(List<Comment> list) {
        commentRepository.deleteAll(list);
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(NotFoundCommentException::new);
    }

    public List<Comment> findAllByPostId(Long id) {
        return commentRepository.findAllByPostId(id);
    }

    public List<Comment> findAllByPostIdPage(Long id, Pageable pageable){
        return commentRepository.findAllByPostId(id, pageable);
    }

    public Integer maxGroupOrders(Long postId, int groupNum) {
        return commentRepository.maxGroupOrder(postId, groupNum);
    }

    public Integer maxGroupNum(Long postId) {
        return commentRepository.maxGroupNum(postId);
    }
}
