package com.project.gameInfo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    private Date createDate;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long memberId;
}
