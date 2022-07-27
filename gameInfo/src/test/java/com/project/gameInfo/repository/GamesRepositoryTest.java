package com.project.gameInfo.repository;

import com.project.gameInfo.config.TestConfig;
import com.project.gameInfo.controller.dto.GamesDto;
import com.project.gameInfo.domain.Games;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.xml.crypto.Data;

import java.util.Date;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
class GamesRepositoryTest {

    @Autowired
    private GamesRepository gamesRepository;


    @Test
    @DisplayName("게임 생성")
    public void createGames() {

        //given
        Games games = Games.createGames(getDto());


        //when
        Games save = gamesRepository.save(games);

        //then
        assertThat(save).isSameAs(games);
        assertThat(save.getName()).isEqualTo(games.getName());
    }



    public GamesDto getDto() {
        return GamesDto.builder()
                .name("테스트")
                .company("회사")
                .introduction("테스트 설명")
                .releaseDate(new Date())
                .build();
    }
}