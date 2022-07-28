package com.project.gameInfo.controller;

import com.project.gameInfo.controller.dto.GenreDto;
import com.project.gameInfo.domain.Genre;
import com.project.gameInfo.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GenreController {

    private final GenreService genreService;

    @PostMapping("/manage/genre/new")
    public ResponseEntity<?> createGenre(@RequestBody GenreDto genreDto) {
        Genre genre = new Genre(genreDto.getName());

        Long save = genreService.save(genre);

        return ResponseEntity.ok(save);
    }

    @GetMapping("/manage/genre/list")
    public ResponseEntity<?> genreList(@PageableDefault(size = 15) Pageable pageable) {
        return ResponseEntity.ok(genreService.getListByPage(pageable));
    }

    @PutMapping("/manage/genre/update")
    public ResponseEntity<?> updateGenre(@RequestBody GenreDto genreDto) {
        Genre genre = genreService.findById(genreDto.getId());
        genreService.updateGenre(genre, genreDto.getName());

        return ResponseEntity.ok(GenreDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build());
    }

}
