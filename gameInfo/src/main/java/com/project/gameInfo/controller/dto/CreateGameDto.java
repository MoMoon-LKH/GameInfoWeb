package com.project.gameInfo.controller.dto;

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
public class CreateGameDto {

    private String name;
    private String introduction;
    private String company;
    private Date releaseDate;
    private List<Long> genres;
    private List<Long> platforms;

}
