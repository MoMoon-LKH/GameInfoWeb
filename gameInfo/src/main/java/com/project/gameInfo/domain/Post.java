package com.project.gameInfo.domain;

import com.project.gameInfo.controller.dto.PostDto;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    @OneToOne
    private Category category;

    public Post() {
    }

    private Post(PostDto postDto) {

    }

    public static Post createPost(PostDto postDto) {
        return new Post(postDto);
    }
}
