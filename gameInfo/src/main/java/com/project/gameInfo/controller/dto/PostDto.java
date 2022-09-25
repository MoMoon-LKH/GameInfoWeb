package com.project.gameInfo.controller.dto;

import com.project.gameInfo.domain.Post;
import lombok.*;

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

    private String nickname;

    private Long gameId;

    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createDate = post.getCreateDate();
        this.categoryId = post.getCategory().getId();
        this.memberId = post.getMember().getId();
        this.nickname = post.getMember().getNickname();

    }
}
