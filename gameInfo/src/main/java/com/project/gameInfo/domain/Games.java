package com.project.gameInfo.domain;

import com.project.gameInfo.controller.dto.CreateGameDto;
import com.project.gameInfo.controller.dto.GamesDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
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

    @Column(name = "imageUrl")
    private String imageUrl;

    @OneToMany(mappedBy = "games")
    private List<ReviewScore> reviewScores = new ArrayList<>();

    @OneToMany(mappedBy = "games")
    private List<GamesGenre> gamesGenres = new ArrayList<>();

    @OneToMany(mappedBy = "games")
    private List<GamesPlatform> gamesPlatforms = new ArrayList<>();

    @OneToMany(mappedBy = "games")
    private List<Post> posts = new ArrayList<>();



    private Games(CreateGameDto gamesDto , String imageUrl){
        this.name = gamesDto.getName();
        this.introduction = gamesDto.getIntroduction();
        this.company = gamesDto.getCompany();
        this.releaseDate = gamesDto.getReleaseDate();
        this.createDate = new Date();
        this.imageUrl = imageUrl;
    }

    public static Games createGames(CreateGameDto gamesDto, String imageUrl) {
        return new Games(gamesDto, imageUrl);
    }


}
