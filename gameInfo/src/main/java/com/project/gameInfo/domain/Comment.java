package com.project.gameInfo.domain;

import com.project.gameInfo.controller.dto.CommentDto;
import com.project.gameInfo.controller.dto.CreateCommentDto;
import com.project.gameInfo.domain.enums.CommentStatus;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_num")
    private int groupNum;

    @Column(name = "group_order")
    @ColumnDefault("0")
    private int groupOrder;

    private String content;

    @Column(name = "create_date")
    private Date createDate;


    @Enumerated(EnumType.STRING)
    private CommentStatus status;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;


    @OneToMany(mappedBy = "comment")
    private List<CommentLike> commentLikes = new ArrayList<>();

    @OneToMany(mappedBy = "comment")
    private List<CommentUnlike> commentUnlikes = new ArrayList<>();

    public Comment() {}

    private Comment(CreateCommentDto commentDto, int groupNum, int groupOrder,Post post, Member member, Comment parent) {
        this.parent = parent;
        this.content = commentDto.getContent();
        this.createDate = new Date();
        this.status = CommentStatus.ALIVE;
        this.groupNum = groupNum;
        this.groupOrder = groupOrder;
        this.post = post;
        this.member = member;
    }


    public static Comment createComment(CreateCommentDto commentDto, int groupNum, int groupOrder, Post post, Member member, Comment parent) {
        return new Comment(commentDto, groupNum, groupOrder, post, member, parent);
    }

    public void updateComment(CommentDto commentDto) {
        this.content = commentDto.getContent();
    }

    public void deleteComment() {
        this.status = CommentStatus.DEL;
    }


}
