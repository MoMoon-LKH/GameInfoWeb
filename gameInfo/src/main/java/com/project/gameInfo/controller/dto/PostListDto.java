package com.project.gameInfo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostListDto {

    private Long id;

    private String title;

    private int view;

    private Long memberId;

    private String nickname;

    private int commentCount;

    private Date createDate;

}
