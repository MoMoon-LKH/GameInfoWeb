package com.project.gameInfo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "comment_like")
@Getter
@NoArgsConstructor
public class CommentLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;


    private CommentLike(Member member, Comment comment) {
        this.member = member;
        this.comment = comment;
    }


    public static CommentLike createLike(Member member, Comment comment) {
        return new CommentLike(member, comment);
    }



}
