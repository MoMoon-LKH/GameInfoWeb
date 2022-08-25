package com.project.gameInfo.controller;

import com.project.gameInfo.controller.dto.GenreDto;
import com.project.gameInfo.controller.dto.IdList;
import com.project.gameInfo.domain.Genre;
import com.project.gameInfo.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GenreController {

    private final GenreService genreService;

    @PostMapping("/manage/genre/new")
    public ResponseEntity<?> createGenre(@RequestBody GenreDto genreDto) {
        Genre genre = new Genre(genreDto.getName());

        Long save = genreService.save(genre);


        return ResponseEntity.ok(genre.getName());
    }

    @GetMapping("/manage/genre/list")
    public ResponseEntity<?> genreList(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(genreService.getListByPage(pageable));
    }

    @PutMapping("/manage/genre")
    public ResponseEntity<?> updateGenre(@RequestBody GenreDto genreDto) {
        Genre genre = genreService.findById(genreDto.getId());
        genreService.updateGenre(genre, genreDto.getName());

        return ResponseEntity.ok(GenreDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build());
    }

    @DeleteMapping("/manage/genre")
    public ResponseEntity<?> deleteGenre(@RequestBody IdList ids) {

        List<Genre> genres = genreService.findAllByIds(ids.getIds());

        boolean boolDelete = genreService.deleteAll(genres);

        return ResponseEntity.ok(boolDelete);
    }

    @GetMapping("/manage/genre/search")
    public ResponseEntity<?> searchGenres(@RequestParam("search") String search ,@PageableDefault(size = 20) Pageable pageable) {

        return ResponseEntity.ok(genreService.findAllByName(search, pageable));
    }

}
