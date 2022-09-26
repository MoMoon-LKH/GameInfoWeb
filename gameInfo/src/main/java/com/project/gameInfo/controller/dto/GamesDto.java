package com.project.gameInfo.controller.dto;

import com.project.gameInfo.domain.Games;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GamesDto {

    private Long id;
    private String name;
    private String introduction;
    private String company;
    private Date releaseDate;
    private double reviewScore;
    private String genres;
    private String platform;
    private String imgUrl;


    public GamesDto(Long id, String name, String introduction, String company, Date releaseDate, double reviewScore, String imageUrl) {
        this.id = id;
        this.name = name;
        this.introduction = introduction;
        this.company = company;
        this.releaseDate = releaseDate;
        this.reviewScore = reviewScore;
        this.imgUrl = imageUrl;
    }

    public GamesDto(Games games) {
        this.id = games.getId();
        this.name = games.getName();
    }
}
