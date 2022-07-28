package com.project.gameInfo.repository;

import com.project.gameInfo.config.TestConfig;
import com.project.gameInfo.controller.dto.CreateGameDto;
import com.project.gameInfo.controller.dto.GamesDto;
import com.project.gameInfo.domain.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;

import javax.xml.crypto.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
class GamesRepositoryTest {

    @Autowired
    private GamesRepository gamesRepository;

    @Autowired
    private PlatformRepository platformRepository;

    @Autowired
    private GamesPlatformRepository gamesPlatformRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private GamesGenreRepository gamesGenreRepository;


    @Test
    @DisplayName("게임 생성")
    public void createGames() {

        //given
        Games games = Games.createGames(getDto("테스트"));


        //when
        Games save = gamesRepository.save(games);

        //then
        assertThat(save).isSameAs(games);
        assertThat(save.getName()).isEqualTo(games.getName());
    }

    // DB에 들어있는 값도 있기때문에 findOne으로 찾아서 검증할 것
    @Test
    @DisplayName("게임 찾기")
    public void getList(){

        //given
        Pageable pageable = PageRequest.of(0, 10);
        Games games1 = Games.createGames(getDto("테스트1"));
        Games games2 = Games.createGames(getDto("테스트2"));
        gamesRepository.save(games1);
        gamesRepository.save(games2);

        Platform mobile = platformRepository.save(new Platform("모바일"));
        Platform pc = platformRepository.save(new Platform("PC"));

        Genre rpg = genreRepository.save(new Genre("RPG"));
        Genre mmorpg = genreRepository.save(new Genre("MMORPG"));

        gamesPlatformRepository.save(new GamesPlatform(games1, mobile));
        gamesPlatformRepository.save(new GamesPlatform(games1, pc));
        gamesPlatformRepository.save(new GamesPlatform(games2, pc));

        gamesGenreRepository.save(new GamesGenre(games1, rpg));
        gamesGenreRepository.save(new GamesGenre(games1, mmorpg));
        gamesGenreRepository.save(new GamesGenre(games2, mmorpg));

        //when
        Games find1 = gamesRepository.findById(games1.getId()).get();
        Games find2 = gamesRepository.findById(games2.getId()).get();

        List<GamesDto> dtoList = new ArrayList<>();
        List<Games> list = new ArrayList<>();
        list.add(find1);
        list.add(find2);

        for (Games games : list) {
            String genres = gamesGenreRepository.findGenresByGamesId(games.getId()).replace(",", " ");
            String platform = gamesPlatformRepository.findPlatformsByGamesId(games.getId()).replace(",", " ");;
            dtoList.add(convertDto(games, genres, platform));
        }


        //then
        assertThat(dtoList.size()).isEqualTo(2);
        assertThat(dtoList.get(0).getGenres()).isEqualTo("RPG MMORPG");
        assertThat(dtoList.get(0).getPlatform()).isEqualTo("모바일 PC");
        assertThat(dtoList.get(0).getId()).isEqualTo(games1.getId());
        assertThat(dtoList.get(1).getId()).isEqualTo(games2.getId());
        assertThat(dtoList.get(0).getName()).isEqualTo(games1.getName());
        assertThat(dtoList.get(1).getName()).isEqualTo(games2.getName());
    }



    public CreateGameDto getDto(String name) {
        return CreateGameDto.builder()
                .name(name)
                .company("회사")
                .introduction(name + " 설명")
                .releaseDate(new Date())
                .build();
    }

    public GamesDto convertDto(Games games, String genre, String platform) {
        return GamesDto.builder()
                .id(games.getId())
                .name(games.getName())
                .introduction(games.getIntroduction())
                .company(games.getCompany())
                .releaseDate(games.getReleaseDate())
                .genres(genre)
                .platform(platform)
                .build();
    }

}