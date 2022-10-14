package com.project.gameInfo.controller.dto;

import com.project.gameInfo.domain.enums.CommentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;

    private Long memberId;

    private String nickname;

    private String parentNickname;

    private String content;

    private int likes;

    private int unlikes;

    private CommentStatus status;

    private int groupNum;

    private int groupOrder;

}
