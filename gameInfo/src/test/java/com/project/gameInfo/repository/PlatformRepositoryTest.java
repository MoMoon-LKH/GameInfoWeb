package com.project.gameInfo.repository;

import com.project.gameInfo.config.TestConfig;
import com.project.gameInfo.domain.Platform;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.parameters.P;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
class PlatformRepositoryTest {

    @Autowired
    private PlatformRepository platformRepository;
    
    @Test
    @DisplayName("플랫폼 저장")
    public void createPlatform(){

        //given
        Platform platform = new Platform("테스트");

        //when
        Platform save = platformRepository.save(platform);

        //then
        assertThat(save).isSameAs(platform);
        assertThat(save.getName()).isEqualTo(platform.getName());
    }
    
    
    @Test
    @DisplayName("게임 플랫폼 리스트")
    public void findAllByGamesId() {

    }

}