package com.project.gameInfo.controller;

import com.project.gameInfo.service.GamesService;
import com.project.gameInfo.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class ImageController {

    private final GamesService gamesService;

    private final ImageService imageService;


    @PostMapping("/image/new")
    public ResponseEntity<?> saveImages(List<MultipartFile> files) {

        return ResponseEntity.ok("");
    }




}
