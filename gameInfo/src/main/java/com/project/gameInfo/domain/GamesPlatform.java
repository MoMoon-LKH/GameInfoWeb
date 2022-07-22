package com.project.gameInfo.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity(name = "games_platform")
@Getter
public class GamesPlatform {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "games_id")
    private Games games;

    @ManyToOne
    @JoinColumn(name = "platform_id")
    private Platform platform;
}
