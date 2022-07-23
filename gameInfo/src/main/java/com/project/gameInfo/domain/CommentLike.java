package com.project.gameInfo.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "comment_like")
@Getter
public class CommentLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;


}
