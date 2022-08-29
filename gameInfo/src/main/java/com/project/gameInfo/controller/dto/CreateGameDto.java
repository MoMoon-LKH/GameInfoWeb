package com.project.gameInfo.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateGameDto {

    @NotNull
    private String name;

    @NotNull
    private String introduction;

    private String company;

    @JsonProperty(namespace = "release_data")
    private Date releaseDate;

    private Long imgId;

    private List<Long> genres;

    private List<Long> platforms;

}
