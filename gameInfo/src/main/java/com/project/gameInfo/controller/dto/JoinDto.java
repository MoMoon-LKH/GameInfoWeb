package com.project.gameInfo.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinDto {

    @NotNull
    private String memberId;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String nickname;

    @NotNull
    private String phone;

    @NotNull
    private String email;

    private String status;

    private String roles;
}
