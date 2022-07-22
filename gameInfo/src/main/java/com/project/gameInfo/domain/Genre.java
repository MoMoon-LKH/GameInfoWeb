package com.project.gameInfo.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Genre {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(mappedBy = "genre")
    private Games games;
}
