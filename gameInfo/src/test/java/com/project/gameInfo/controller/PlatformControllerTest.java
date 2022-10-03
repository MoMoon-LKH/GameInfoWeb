package com.project.gameInfo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gameInfo.controller.dto.IdList;
import com.project.gameInfo.controller.dto.PlatformDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
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
class PlatformControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @Transactional
    @DisplayName("플랫폼 생성")
    public void createPlatform() throws Exception{

        Map<String, String> map = new HashMap<>();
        map.put("name", "platform");

        mockMvc.perform(
                post("/api/manage/platform/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(map))
                        .with(user("james").roles("MANAGE"))
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document(
                                "platform-create",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                    fieldWithPath("name").description("이름")
                                )
                        )
                );
    }


    @Test
    @DisplayName("플랫폼 리스트")
    public void platformList() throws Exception{

        mockMvc.perform(
                        get("/api/manage/platform/list")
                                .param("page", "0")
                                .with(user("james").roles("MANAGE"))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("platform-list",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("page").description("해당 페이지")
                                ),
                                responseFields(
                                        fieldWithPath("[].id").description("id"),
                                        fieldWithPath("[].name").description("플랫폼 이름")
                                )
                        )
                );
    }


    @Test
    @DisplayName("플랫폼 검색")
    public void searchPlatform() throws Exception{

        mockMvc.perform(
                        get("/api/manage/platform/search")
                                .param("page", "0")
                                .param("search", "PS")
                                .with(user("james").roles("MANAGE"))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("platform-search",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("search").description("플랫폼 검색어"),
                                        parameterWithName("page").description("해당 페이지")
                                ),
                                responseFields(
                                        fieldWithPath("[].id").description("id"),
                                        fieldWithPath("[].name").description("플랫폼 이름")
                                )
                        )
                );
    }

    @Test
    @Transactional
    @DisplayName("플랫폼 수정")
    public void updatePlatform() throws Exception {

        Long id = platformCreate("platform");

        PlatformDto platformDto = PlatformDto.builder()
                .id(id)
                .name("updatePlatform").build();

        mockMvc.perform(
                        put("/api/manage/platform")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(platformDto))
                                .with(user("james").roles("MANAGE"))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("platform-update",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("id").description("id"),
                                        fieldWithPath("name").description("변경할 이름")
                                ),
                                responseFields(
                                        fieldWithPath("id").description("id"),
                                        fieldWithPath("name").description("변경한 이름")
                                )
                        )
                );
    }


    @Test
    @Transactional
    @DisplayName("플랫폼 삭제")
    public void deletePlatform() throws Exception {

        Long id1 = platformCreate("platform");
        Long id2 = platformCreate("platform2");

        List<Long> list = new ArrayList<>();
        list.add(id1);
        list.add(id2);

        mockMvc.perform(
                        delete("/api/manage/platform")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(IdList.builder().ids(list).build()))
                                .with(user("james").roles("MANAGE"))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("platform-delete",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("ids").description("삭제할 id들")
                                )
                        )
                );
    }


    protected Long platformCreate(String name) throws Exception{

        Map<String, String> map = new HashMap<>();
        map.put("name", name);

        MvcResult mvcResult = mockMvc.perform(
                        post("/api/manage/platform/new")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(map))
                                .with(user("james").roles("MANAGE"))
                )
                .andReturn();

        return Long.parseLong(mvcResult.getResponse().getContentAsString());

    }


}