package com.project.gameInfo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostListDto {

    private Long id;

    private String title;

    private Long categoryId;

    private String categoryName;

    private Long commentCount;
}
