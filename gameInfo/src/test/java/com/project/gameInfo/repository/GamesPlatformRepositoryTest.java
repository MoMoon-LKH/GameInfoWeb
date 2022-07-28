package com.project.gameInfo.repository;

import com.project.gameInfo.config.TestConfig;
import com.project.gameInfo.controller.dto.CreateGameDto;
import com.project.gameInfo.domain.Games;
import com.project.gameInfo.domain.GamesPlatform;
import com.project.gameInfo.domain.Platform;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
class GamesPlatformRepositoryTest {

    @Autowired
    private GamesPlatformRepository gamesPlatformRepository;

    @Autowired
    private PlatformRepository platformRepository;

    @Autowired
    private GamesRepository gamesRepository;
    
    
    @Test
    @DisplayName("게임,플랫폼 연관관계 설정")
    public void saveGamesPlatform() {

        // given
        List<Games> games = gamesRepository.findAll();
        List<Platform> platforms = platformRepository.findAll();

        if (!(games.size() > 0 && platforms.size() > 0)) {
            if (games.size() > 0) {
                platforms.add(platformRepository.save(new Platform("테스트")));
            } else{
                games.add(gamesRepository.save(Games.createGames(CreateGameDto.builder().name("테스트").build())));

            }
        }

        //when
        GamesPlatform gamesPlatform = new GamesPlatform(games.get(0), platforms.get(0));
        gamesPlatformRepository.save(gamesPlatform);


        //then
        assertThat(gamesPlatform.getGames()).isSameAs(games.get(0));
        assertThat(gamesPlatform.getPlatform()).isSameAs(platforms.get(0));

    }

    @Test
    @DisplayName("게임 플랫폼들 출력")
    public void platformString() {

        //given
        Games game = gamesRepository.save(Games.createGames(createGameDto("테스트")));
        gamesRepository.save(game);
        Platform platform1 = new Platform("테스트1");
        Platform platform2 = new Platform("테스트2");
        platformRepository.save(platform1);
        platformRepository.save(platform2);
        gamesPlatformRepository.save(new GamesPlatform(game, platform1));
        gamesPlatformRepository.save(new GamesPlatform(game, platform2));

        //when
        String gamePlatform = gamesPlatformRepository.findPlatformsByGamesId(game.getId());

        //then
        assertThat(gamePlatform).isEqualTo("테스트1,테스트2");

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