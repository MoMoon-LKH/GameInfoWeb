package com.project.gameInfo.controller.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class LoginDto {

    @NotNull
    private String memberId;

    @NotNull
    private String password;
}
