package com.project.gameInfo.domain;

import com.project.gameInfo.controller.dto.CommentDto;
import com.project.gameInfo.controller.dto.CreateCommentDto;
import com.project.gameInfo.domain.enums.CommentStatus;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reply_id")
    private Long replyId;

    private String content;

    @Column(name = "create_date")
    private Date createDate;


    @Enumerated(EnumType.STRING)
    private CommentStatus status;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "comment")
    private List<CommentLike> commentLikes = new ArrayList<>();

    @OneToMany(mappedBy = "comment")
    private List<CommentUnlike> commentUnlikes = new ArrayList<>();

    public Comment() {}

    private Comment(CreateCommentDto commentDto, Post post, Member member) {
        this.replyId = commentDto.getReplyId();
        this.content = commentDto.getContent();
        this.createDate = new Date();
        this.status = CommentStatus.ALIVE;
        this.post = post;
        this.member = member;
    }


    public static Comment createComment(CreateCommentDto commentDto, Post post, Member member) {
        return new Comment(commentDto, post, member);
    }

    public void updateComment(CommentDto commentDto) {
        this.content = commentDto.getContent();
    }

    public void deleteComment() {
        this.status = CommentStatus.DEL;
    }


}
