package com.project.gameInfo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class TokenDto {

    @NotNull
    private String accessToken;

    @NotNull
    private String refreshToken;
}
