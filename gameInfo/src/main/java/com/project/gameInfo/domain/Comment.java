package com.project.gameInfo.domain;

import com.project.gameInfo.controller.dto.CommentDto;
import com.project.gameInfo.domain.enums.CommentStatus;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reply_id")
    private Long replyId;

    @Column(name = "member_id")
    private Long memberId;

    private String nickname;

    private String content;

    @Column(name = "create_date")
    private Date createDate;

    @Enumerated(EnumType.STRING)
    private CommentStatus status;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment() {}

    private Comment(CommentDto commentDto) {

        this.createDate = new Date();
        this.status = CommentStatus.ALIVE;
    }


    public static Comment createComment(CommentDto commentDto) {
        return new Comment(commentDto);
    }


}
