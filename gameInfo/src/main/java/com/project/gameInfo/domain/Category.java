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

    @Column(length = 20)
    private String name;


    public Category() {
    }

    private Category(CategoryDto categoryDto) {
        this.name = categoryDto.getName();
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }



    public static Category createCategory(CategoryDto categoryDto) {
        return new Category(categoryDto);
    }


    public void updateName(String name) {
        this.name = name;
    }

}
