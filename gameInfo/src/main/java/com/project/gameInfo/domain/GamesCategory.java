package com.project.gameInfo.domain;


import lombok.Getter;

import javax.persistence.*;

@Entity(name = "games_category")
@Getter
public class GamesCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "games_id")
    private Games games;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    public GamesCategory(Games games, Category category) {
        this.games = games;
        this.category = category;
    }
}
