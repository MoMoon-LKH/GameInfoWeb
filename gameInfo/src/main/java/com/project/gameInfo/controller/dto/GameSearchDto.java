package com.project.gameInfo.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameSearchDto {

    private String column;
    private String search;
}
