package com.project.gameInfo.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class GamesDto {

    private Long id;
    private String name;
    private String introduction;
    private String company;
    private Date releaseDate;
    private int reviewScore;
    private String genre;
    private String platform;

}
