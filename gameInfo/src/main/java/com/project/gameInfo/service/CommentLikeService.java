package com.project.gameInfo.service;

import com.project.gameInfo.domain.CommentDislike;
import com.project.gameInfo.domain.CommentLike;
import com.project.gameInfo.repository.CommentDislikeRepository;
import com.project.gameInfo.repository.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    private final CommentDislikeRepository commentDislikeRepository;

    @Transactional
    public CommentLike saveLike(CommentLike like) {
        return commentLikeRepository.save(like);
    }

    @Transactional
    public CommentDislike saveDislike(CommentDislike dislike) {
        return commentDislikeRepository.save(dislike);
    }

    @Transactional
    public void delLike(CommentLike like) {
        commentLikeRepository.delete(like);
    }

    @Transactional
    public void delDislike(CommentDislike dislike) {
        commentDislikeRepository.delete(dislike);
    }


    public Optional<CommentLike> findCommentLike(Long commentId, Long memberId) {
        return commentLikeRepository.findByCommentIdAndMemberId(commentId, memberId);
    }

    public Optional<CommentDislike> findCommentDislike(Long commentId, Long memberId) {
        return commentDislikeRepository.findByCommentIdAndMemberId(commentId, memberId);
    }


}

