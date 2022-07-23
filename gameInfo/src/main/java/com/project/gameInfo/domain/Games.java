package com.project.gameInfo.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
public class Games {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String introduction;

    private String company;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "create_date")
    private Date createDate;

    @OneToMany(mappedBy = "games")
    private List<ReviewScore> reviewScores = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToMany(mappedBy = "games")
    private List<GamesPlatform> gamesPlatforms = new ArrayList<>();

    @OneToMany(mappedBy = "games")
    private List<GamesCategory> gamesCategories = new ArrayList<>();



}
