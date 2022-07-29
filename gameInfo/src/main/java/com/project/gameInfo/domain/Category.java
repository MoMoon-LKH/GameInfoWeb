package com.project.gameInfo.domain;

import com.project.gameInfo.controller.dto.CategoryDto;
import com.project.gameInfo.domain.enums.CategoryState;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "category")
    private List<GamesCategory> gamesCategories = new ArrayList<>();


    public Category() {
    }

    private Category(CategoryDto categoryDto) {
        this.name = categoryDto.getName();
        this.parentId = categoryDto.getParentId();
        this.state = CategoryState.ALIVE;
    }

    public Category(Long id, String name, Long parentId, CategoryState state) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.state = state;
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
