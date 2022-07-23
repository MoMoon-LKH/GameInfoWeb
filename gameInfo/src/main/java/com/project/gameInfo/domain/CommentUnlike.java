package com.project.gameInfo.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity(name = "comment_unlike")
@Getter
public class CommentUnlike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
