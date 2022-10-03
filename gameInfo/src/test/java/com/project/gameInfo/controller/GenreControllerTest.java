package com.project.gameInfo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gameInfo.controller.dto.GenreDto;
import com.project.gameInfo.controller.dto.IdList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class GenreControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @Transactional
    @DisplayName("장르 생성")
    public void createGenre() throws Exception{

        Map<String, String> map = new HashMap<>();
        map.put("name", "RTS");

        mockMvc.perform(
                post("/api/manage/genre/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(map))
                        .with(user("james").roles("MANAGE"))
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("genre-create",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("id").description("id"),
                                        fieldWithPath("name").description("이름")
                                )
                        )
                );
    }

    @Test
    @DisplayName("장르 리스트")
    public void genreList() throws Exception{

        mockMvc.perform(
                        get("/api/manage/genre/list")
                                .param("page", "0")
                                .with(user("james").roles("MANAGE"))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("genre-list",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                    parameterWithName("page").description("리스트 페이지")
                                ),
                                responseFields(
                                        fieldWithPath("[].id").description("id"),
                                        fieldWithPath("[].name").description("이름")
                                )
                        ));
    }

    @Test
    @DisplayName("검색 리스트")
    public void searchList() throws Exception{

        mockMvc.perform(
                get("/api/manage/genre/search")
                        .with(user("james").roles("MANAGE"))
                        .param("search", "F")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("genre-search",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("search").description("검색어")
                                ),
                                responseFields(
                                        fieldWithPath("[].id").description("id"),
                                        fieldWithPath("[].name").description("이름")
                                )
                        ));
    }

    @Test
    @Transactional
    @DisplayName("장르 수정")
    public void updateGenre() throws Exception{

        Map<String, Object> map = CreateGenre("test");

        GenreDto genreDto = GenreDto.builder()
                .id(Long.parseLong(map.get("id").toString()))
                .name(map.get("name").toString()).build();

        mockMvc.perform(
                put("/api/manage/genre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreDto))
                        .with(user("james").roles("MANAGE"))
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document(
                                "genre-update",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("id").description("변경할 장르 id"),
                                        fieldWithPath("name").description("변경할 이름")
                                ),
                                responseFields(
                                        fieldWithPath("id").description("id"),
                                        fieldWithPath("name").description("변경 완료된 이름")
                                )
                        )
                );
    }


    @Test
    @Transactional
    @DisplayName("장르 삭제")
    public void deleteGenre() throws Exception{

        Map<String, Object> map = CreateGenre("test");
        Map<String, Object> map2 = CreateGenre("test2");

        List<Long> list = new ArrayList<>();
        list.add(Long.parseLong(map.get("id").toString()));
        list.add(Long.parseLong(map2.get("id").toString()));

        IdList idList = IdList.builder().ids(list).build();

        System.out.println("idList = " + idList.toString());

        mockMvc.perform(
                        delete("/api/manage/genre")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(idList))
                                .with(user("james").roles("MANAGE"))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("genre-delete",
                                preprocessRequest(prettyPrint()),
                                requestFields(
                                        fieldWithPath("ids").description("삭제할 장르 아이디들")
                                ))
                );
    }


    protected Map CreateGenre(String name) throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("name", name);

        MvcResult mvcResult = mockMvc.perform(
                        post("/api/manage/genre/new")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(map))
                                .with(user("james").roles("MANAGE"))
                )

                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Map.class);

    }

}