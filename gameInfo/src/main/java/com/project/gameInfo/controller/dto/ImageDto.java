package com.project.gameInfo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ImageDto {

    private Long id;

    private String origin_name;

    private String url;
}
