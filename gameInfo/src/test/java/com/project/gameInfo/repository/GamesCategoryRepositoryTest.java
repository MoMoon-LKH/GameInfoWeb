package com.project.gameInfo.repository;

import com.project.gameInfo.config.TestConfig;
import com.project.gameInfo.controller.dto.CategoryDto;
import com.project.gameInfo.controller.dto.CreateGameDto;
import com.project.gameInfo.domain.Category;
import com.project.gameInfo.domain.Games;
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
@Import(TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GamesCategoryRepositoryTest {

    @Autowired
    private GamesCategoryRepository gamesCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private GamesRepository gamesRepository;


    @Test
    @DisplayName("게임 카테고리 관계 설정")
    public void saveGamesCategory() {

        //given
        Games games = gamesRepository.save(Games.createGames(createGameDto("TEST"), "imageUrl"));
        Category category = categoryRepository.save(Category.createCategory(createCategoryDto("test")));

        //when
        GamesCategory save = gamesCategoryRepository.save(new GamesCategory(games, category));

        //then
        assertThat(save.getGames()).isSameAs(games);
        assertThat(save.getCategory()).isSameAs(category);


    }

    @Test
    @DisplayName("게임 카테고리 리스트")
    public void gamesCategoryList() {

        //given
        Games games = gamesRepository.save(Games.createGames(createGameDto("TEST"), "imageUrl"));
        Category category = categoryRepository.save(Category.createCategory(createCategoryDto("test1")));
        Category category2 = categoryRepository.save(Category.createCategory(createCategoryDto("test2")));

        GamesCategory save1 = gamesCategoryRepository.save(new GamesCategory(games, category));
        GamesCategory save2 = gamesCategoryRepository.save(new GamesCategory(games, category2));

        //when
        List<Category> list = gamesCategoryRepository.findCategoryByGamesId(games.getId());

        //then
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0).getName()).isEqualTo(save1.getCategory().getName());
        assertThat(list.get(1).getName()).isEqualTo(save2.getCategory().getName());

    }



    public CreateGameDto createGameDto(String name) {
        return CreateGameDto.builder()
                .name(name)
                .company(name)
                .introduction(name + "설명")
                .releaseDate(new Date())
                .build();
    }

    public CategoryDto createCategoryDto(String name) {
        return CategoryDto.builder()
                .name(name)
                .parentId(0L)
                .build();
    }

}