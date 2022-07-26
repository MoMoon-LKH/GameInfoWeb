package com.project.gameInfo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "games_genre")
@Getter
@NoArgsConstructor
public class GamesGenre {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "games_id")
    private Games games;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public GamesGenre(Games games, Genre genre) {
        this.games = games;
        this.genre = genre;
    }

}
