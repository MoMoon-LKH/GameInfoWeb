package com.project.gameInfo.controller.dto;

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

    private Date releaseDate;

    private List<Long> genres;

    private List<Long> platforms;

}
