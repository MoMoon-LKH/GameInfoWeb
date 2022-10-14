package com.project.gameInfo.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentDto {

    private Long parentId;

    @NotNull
    private Long memberId;

    @NotNull
    private String content;

    @NotNull
    private Long postId;


}
