package com.project.gameInfo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gameInfo.controller.dto.CreateGameDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class GamesControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @DisplayName("게임 생성")
    @Transactional
    public void createGame() throws Exception {

        List<Long> genres = new ArrayList<>();
        List<Long> platforms = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        genres.add(1L);
        genres.add(2L);
        platforms.add(1L);
        platforms.add(2L);


        CreateGameDto createGameDto = CreateGameDto.builder()
                .name("game test")
                .introduction("game introduction")
                .company("company")
                .releaseDate(format.parse("2022-10-01"))
                .imgId(1L)
                .genres(genres)
                .platforms(platforms)
                .build();

        mockMvc.perform(
                        post("/api/manage/games/new")
                                .with(user("james").roles("MANAGE"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createGameDto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("game-create",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("name").description("게임명"),
                                        fieldWithPath("introduction").description("게임 소개"),
                                        fieldWithPath("company").description("제작회사"),
                                        fieldWithPath("release_date").description("발매일"),
                                        fieldWithPath("img_id").description("표지 이미지 id"),
                                        fieldWithPath("genres").description("게임 장르"),
                                        fieldWithPath("platforms").description("게임 플랫폼")
                                ),
                                responseFields(
                                        fieldWithPath("id").description("id"),
                                        fieldWithPath("name").description("게임명")
                                )
                        )
                );
    }


    @Test
    @DisplayName("게임 조회")
    public void findGame() throws Exception{

        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/api/all/games/{id}", 1)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("games-get",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("id").description("id")
                                ),
                                responseFields(
                                        fieldWithPath("id").description("게임 아이디"),
                                        fieldWithPath("name").description("게임명"),
                                        fieldWithPath("introduction").description("게임 소개"),
                                        fieldWithPath("company").description("개발 회사"),
                                        fieldWithPath("releaseDate").description("발매일"),
                                        fieldWithPath("reviewScore").description("게임 평점"),
                                        fieldWithPath("genres").description("게임 장르들"),
                                        fieldWithPath("platform").description("게임 플랫폼들"),
                                        fieldWithPath("imgUrl").description("게임 표지 이미지")
                                )
                        )
                );
    }

    @Test
    @DisplayName("게임 리스트")
    public void gamesList() throws Exception {

        mockMvc.perform(
                        get("/api/all/games/list")
                                .param("page", "0")
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("games-list",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("page").description("해당 페이지")
                                ),
                                responseFields(
                                        fieldWithPath("[].id").description("게임 아이디"),
                                        fieldWithPath("[].name").description("게임명"),
                                        fieldWithPath("[].introduction").description("게임 소개"),
                                        fieldWithPath("[].company").description("개발 회사"),
                                        fieldWithPath("[].releaseDate").description("발매일"),
                                        fieldWithPath("[].reviewScore").description("게임 평점"),
                                        fieldWithPath("[].genres").description("게임 장르들"),
                                        fieldWithPath("[].platform").description("게임 플랫폼들"),
                                        fieldWithPath("[].imgUrl").description("게임 표지 이미지")
                                )
                        )
                );
    }

    @Test
    @DisplayName("게임 검색 리스트")
    public void searchList() throws Exception{

        mockMvc.perform(
                        get("/api/all/games/search")
                                .param("search", "다")
                                .param("page", "0")
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("games-search",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("search").description("검색어"),
                                        parameterWithName("page").description("해당 페이지")
                                ),
                                responseFields(
                                        fieldWithPath("[].id").description("게임 아이디"),
                                        fieldWithPath("[].name").description("게임명"),
                                        fieldWithPath("[].introduction").description("게임 소개"),
                                        fieldWithPath("[].company").description("개발 회사"),
                                        fieldWithPath("[].releaseDate").description("발매일"),
                                        fieldWithPath("[].reviewScore").description("게임 평점"),
                                        fieldWithPath("[].genres").description("게임 장르들"),
                                        fieldWithPath("[].platform").description("게임 플랫폼들"),
                                        fieldWithPath("[].imgUrl").description("게임 표지 이미지")
                                )
                        )
                );
    }


}