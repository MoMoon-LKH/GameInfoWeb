package com.project.gameInfo.controller;


import com.project.gameInfo.controller.dto.CategoryDto;
import com.project.gameInfo.domain.Category;
import com.project.gameInfo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;


  /*  @PostMapping("/manage/category/new/{gamesId}")
    public ResponseEntity<?> createCategory(
            @RequestBody List<CategoryDto> categoryDtos,
            @PathVariable("gamesId") Long id
            ) {

        for (CategoryDto categoryDto : categoryDtos) {
            categoryService.save(Category.createCategory(categoryDto));
        }

        List<CategoryDto> list = new ArrayList<>();



        return ResponseEntity.ok(list);
    }

*/
/*
    @PutMapping("/manage/category/{categoryId}")
    public ResponseEntity<?> updateName(@PathVariable("categoryId") Long id,
                                        @RequestBody CategoryDto categoryDto
    ) {

        Category category = categoryService.findByCategoryId(id);
        categoryService.updateName(category, categoryDto.getName());

        return ResponseEntity.ok(convertCategoryDto(category));
    }
*/


  /*  @DeleteMapping("/manage/category/{categoryId}")
    public ResponseEntity<?> deleteCategory(
            @PathVariable("categoryId") Long id
    ) {

        Category category = categoryService.findByCategoryId(id);
        categoryService.deleteState(category);

        return ResponseEntity.ok("삭제 완료");
    }
*/

  /*  public CategoryDto convertCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .parentId(category.getParentId()).build();
    }*/
}
