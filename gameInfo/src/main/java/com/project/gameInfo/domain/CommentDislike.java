package com.project.gameInfo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "comment_dislike")
@Getter
@NoArgsConstructor
public class CommentDislike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


    private CommentDislike(Member member, Comment comment) {
        this.member = member;
        this.comment = comment;
    }

    public static CommentDislike createUnlike(Member member, Comment comment) {
        return new CommentDislike(member, comment);
    }
}
