package com.project.gameInfo.service;

import com.project.gameInfo.controller.dto.CategoryDto;
import com.project.gameInfo.domain.Category;
import com.project.gameInfo.domain.enums.CategoryState;
import com.project.gameInfo.repository.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;
    
    
    @Test
    @DisplayName("카테고리 저장")
    void saveCategory(){

        //given
        CategoryDto categoryDto = CategoryDto.builder()
                .name("category").parentId(0L).build();
        Category category = Category.createCategory(categoryDto);
        when(categoryRepository.save(any())).thenReturn(category);

        //when
        Long save = categoryService.save(category);
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        Category find = categoryService.findByCategoryId(save);

        //then
        assertThat(category).isSameAs(find);


    }

    
    @Test
    @DisplayName("카테고리 이름 변경")
    void updateName(){

        //given
        String updateName = "update";
        Category category = generateCategory();
        when(categoryRepository.save(any())).thenReturn(category);

        categoryService.save(category);
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

        //when
        Category find = categoryService.findByCategoryId(category.getId());
        categoryService.updateName(find,updateName);

        //then
        assertThat(find.getName()).isEqualTo(updateName);

    }
    
    @Test
    @DisplayName("카테고리 삭제 상태 변경")
    void updateDeleteState() {

        //given
        Category category = generateCategory();
        when(categoryRepository.save(any())).thenReturn(category);
        categoryService.save(category);

        //when
        categoryService.deleteState(category);

        //then
        assertThat(category.getState()).isEqualTo(CategoryState.DELETE);
    }


    Category generateCategory(){
        CategoryDto categoryDto = CategoryDto.builder()
                .name("category").parentId(0L).build();

        return Category.createCategory(categoryDto);
    }



}