package com.project.gameInfo.service;

import com.project.gameInfo.controller.dto.PostDto;
import com.project.gameInfo.domain.Post;
import com.project.gameInfo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
