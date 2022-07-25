package com.project.gameInfo.controller;

import com.project.gameInfo.controller.dto.PostDto;
import com.project.gameInfo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;



    @PostMapping("/new")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto) {

        return ResponseEntity.ok("");
    }


}
