package com.project.gameInfo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostDto {


    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long memberId;

    private Long gameId;


}
