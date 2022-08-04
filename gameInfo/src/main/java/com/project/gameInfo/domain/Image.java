package com.project.gameInfo.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Image {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String origin_name;

    private String url;


    public Image() {

    }

    public Image(String origin_name, String url) {
        this.origin_name = origin_name;
        this.url = url;
    }

    public static Image createImage(String origin_name ,String url) {
        return new Image(origin_name, url);
    }

}
