package com.project.gameInfo.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Image {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String src;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;


    public Image() {

    }

    public Image(String src, Post post) {
        this.src = src;
        this.post = post;
    }

    public static Image createImage(String src, Post post) {
        return new Image(src, post);
    }

}
