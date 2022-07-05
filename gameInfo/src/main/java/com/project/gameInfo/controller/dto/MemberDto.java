package com.project.gameInfo.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class MemberDto {

    @NotNull
    private String memberId;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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
}
