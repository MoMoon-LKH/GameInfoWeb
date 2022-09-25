package com.project.gameInfo.service;

import com.project.gameInfo.controller.dto.CreatePostDto;
import com.project.gameInfo.controller.dto.PostDto;
import com.project.gameInfo.controller.dto.PostListDto;
import com.project.gameInfo.domain.Post;
import com.project.gameInfo.exception.NotFoundPostException;
import com.project.gameInfo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    @Transactional
    public Long save(Post post) {
        return postRepository.save(post).getId();
    }

    @Transactional
    public void updatePost(Post post, PostDto postDto) {
        post.updatePost(postDto);
    }

    @Transactional
    public void addView(Post post) {
        post.addView();
    }

    @Transactional
    public void delete(Post post) {
        postRepository.delete(post);
    }


    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(NotFoundPostException::new);
    }

    public List<PostListDto> findAllByCategoryAndGamePage(Long categoryId, Long gameId, Pageable pageable) {
        return postRepository.findAllByCategoryIdAndGamePage(categoryId, gameId, pageable);
    }

    public List<PostListDto> findAllByCategory(Long categoryId, Pageable pageable) {
        return postRepository.findAllByCategoryId(categoryId, pageable);
    }

    public Long countByCategoryIdAndGamesId(Long categoryId, Long gameId) {
        return postRepository.countByCategoryAndGamePage(categoryId, gameId);
    }
}
