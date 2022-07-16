package com.project.gameInfo.repository;

import com.project.gameInfo.controller.dto.CategoryDto;
import com.project.gameInfo.domain.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;
    
    
    @Test
    @DisplayName("카테고리 저장")
    void saveCategory() {

        //given
        Category category = generateCategory();

        //when
        Category save = categoryRepository.save(category);

        //then
        assertThat(save).isSameAs(category);
        assertThat(save.getId()).isEqualTo(category.getId());
    }


    Category generateCategory() {
        CategoryDto categoryDto = CategoryDto.builder()
                .name("category")
                .parentId(0L)
                .build();

        return Category.createCategory(categoryDto);
    }

}