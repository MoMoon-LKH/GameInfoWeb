package com.project.gameInfo.controller;

import com.project.gameInfo.controller.dto.CreateGameDto;
import com.project.gameInfo.controller.dto.GamesDto;
import com.project.gameInfo.domain.Games;
import com.project.gameInfo.domain.Genre;
import com.project.gameInfo.domain.Platform;
import com.project.gameInfo.repository.GamesPlatformRepository;
import com.project.gameInfo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GamesController {

    private final GamesService gamesService;
    private final GamesPlatformService gamesPlatformService;
    private final PlatformService platformService;
    private final GamesGenreService gamesGenreService;
    private final GenreService genreService;
    private final ReviewScoreService reviewScoreService;


    @PostMapping("/manage/games/new")
    public ResponseEntity<?> createGames(@RequestBody CreateGameDto gameDto) {

        Games games = Games.createGames(gameDto);
        gamesService.save(games);

        for (Long id : gameDto.getGenres()) {
            Genre genre = genreService.findById(id);
            gamesGenreService.save(games, genre);
        }

        for (Long id : gameDto.getPlatforms()) {
            Platform platform = platformService.findById(id);
            gamesPlatformService.save(games, platform);
        }


        return ResponseEntity.ok(games.getId());
    }


    @GetMapping("/all/games/list")
    public ResponseEntity<?> listByPage(
            @PageableDefault(sort = "release_date", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        List<GamesDto> gamesDtos = gamesService.findAllByPage(pageable);

        for (GamesDto gamesDto : gamesDtos) {
            gamesDto.setReviewScore(reviewScoreService.findScoreByGamesId(gamesDto.getId()));
            gamesDto.setGenres(gamesGenreService.findGenresByGamesId(gamesDto.getId()));
            gamesDto.setPlatform(gamesPlatformService.findPlatformsByGamesId(gamesDto.getId()));
        }

        return ResponseEntity.ok(gamesDtos);
    }

    @GetMapping("/all/games/search")
    public ResponseEntity<?> searchList(
            @RequestParam String search,
            @PageableDefault(sort = "release_date", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        List<GamesDto> gamesDtos = gamesService.findAllBySearch(search, pageable);

        for (GamesDto gamesDto : gamesDtos) {
            gamesDto.setReviewScore(reviewScoreService.findScoreByGamesId(gamesDto.getId()));
            gamesDto.setGenres(gamesGenreService.findGenresByGamesId(gamesDto.getId()));
            gamesDto.setPlatform(gamesPlatformService.findPlatformsByGamesId(gamesDto.getId()));
        }

        return ResponseEntity.ok(gamesDtos);
    }


    @GetMapping("/all/games/{id}")
    public ResponseEntity<?> getGame(@PathVariable("id") Long id) {

        GamesDto game = gamesService.findDtoById(id);
        game.setReviewScore(reviewScoreService.findScoreByGamesId(game.getId()));
        game.setGenres(gamesGenreService.findGenresByGamesId(game.getId()));
        game.setPlatform(gamesPlatformService.findPlatformsByGamesId(game.getId()));

        return ResponseEntity.ok(game);
    }
}
