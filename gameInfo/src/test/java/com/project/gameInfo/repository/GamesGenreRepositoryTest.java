package com.project.gameInfo.repository;

import com.project.gameInfo.config.TestConfig;
import com.project.gameInfo.controller.dto.CreateGameDto;
import com.project.gameInfo.domain.Games;
import com.project.gameInfo.domain.GamesGenre;
import com.project.gameInfo.domain.Genre;
import com.project.gameInfo.domain.Platform;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
class GamesGenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private GamesRepository gamesRepository;

    @Autowired
    private GamesGenreRepository gamesGenreRepository;


    @Test
    @DisplayName("게임 장르 관계 설정")
    public void saveGamesGenre() {

        //given
        List<Games> games = gamesRepository.findAll();
        List<Genre> genres = genreRepository.findAll();

        if (!(games.size() > 0 && genres.size() > 0)) {
            if (games.size() > 0) {
                genres.add(genreRepository.save(new Genre("테스트")));
            } else{
                games.add(gamesRepository.save(Games.createGames(CreateGameDto.builder().name("테스트").build(), "imageUrl")));

            }
        }

        //when
        GamesGenre gamesGenre = new GamesGenre(games.get(0), genres.get(0));
        gamesGenreRepository.save(gamesGenre);

        //then
        assertThat(gamesGenre.getGames()).isSameAs(games.get(0));
        assertThat(gamesGenre.getGenre()).isSameAs(genres.get(0));

    }
    
    
    @Test
    @DisplayName("게임 장르들 출력")
    public void genreString() {

        //given
        Games game = Games.createGames(createGameDto("테스트"), "imageUrl");
        gamesRepository.save(game);
        Genre genre1 = genreRepository.save(new Genre("TEST1"));
        Genre genre2 = genreRepository.save(new Genre("TEST2"));

        gamesGenreRepository.save(new GamesGenre(game, genre1));
        gamesGenreRepository.save(new GamesGenre(game, genre2));

        //when
        String genre = gamesGenreRepository.findGenresByGamesId(game.getId()).replace(",", " ");

        //then
        assertThat(genre).isEqualTo(genre1.getName() + " " + genre2.getName());

    }


    public CreateGameDto createGameDto(String name) {
        return CreateGameDto.builder()
                .name(name)
                .company(name)
                .introduction(name + "설명")
                .releaseDate(new Date())
                .build();
    }

}