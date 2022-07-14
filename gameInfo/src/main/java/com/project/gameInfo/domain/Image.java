package com.project.gameInfo.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Image {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String src;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

}
