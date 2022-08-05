package com.project.gameInfo.domain;

import com.project.gameInfo.controller.dto.PostDto;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private int view;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();


    @OneToMany(mappedBy = "post")
    private List<ReviewScore> reviewScores = new ArrayList<>();

    public Post() {
    }

    private Post(PostDto postDto, Category category, Member member) {
        this.title = postDto.getTitle();
        this.content = postDto.getContent();
        this.view = 0;
        this.createDate = new Date();
        this.updateDate = new Date();
        this.category = category;
        this.member = member;

    }

    public static Post createPost(PostDto postDto, Category category, Member member) {
        return new Post(postDto, category,member);
    }

    public void updatePost(PostDto postDto) {
        this.title = title;
        this.content = content;
        this.updateDate = new Date();
    }
}
