package com.project.gameInfo.domain;

import com.project.gameInfo.controller.dto.CategoryDto;
import com.project.gameInfo.domain.enums.CategoryState;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "parent_id")
    private Long parentId;

    @Enumerated(EnumType.STRING)
    private CategoryState state;


    public Category() {
    }

    private Category(CategoryDto categoryDto) {
        this.name = categoryDto.getName();
        this.parentId = categoryDto.getParentId();
        this.state = CategoryState.ALIVE;
    }



    public static Category createCategory(CategoryDto categoryDto) {
        return new Category(categoryDto);
    }


    public void updateName(String name) {
        this.name = name;
    }

    public void updateDeleteState() {
        this.state = CategoryState.DELETE;
    }
}
