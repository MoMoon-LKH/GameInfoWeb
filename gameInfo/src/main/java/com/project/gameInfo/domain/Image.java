package com.project.gameInfo.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Image {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origin_name")
    private String originName;

    private String url;


    public Image() {

    }

    public Image(String origin_name, String url) {
        this.originName = origin_name;
        this.url = url;
    }

    public static Image createImage(String originName ,String url) {
        return new Image(originName, url);
    }

}
