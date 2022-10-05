package com.project.gameInfo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gameInfo.controller.dto.CreatePostDto;
import com.project.gameInfo.controller.dto.PostDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @Transactional
    @DisplayName("포스트 생성")
    public void createPost() throws Exception {

        CreatePostDto postDto = CreatePostDto.builder()
                .title("post test")
                .content("content")
                .memberId(1L)
                .categoryId(1L)
                .gameId(1L)
                .build();


        mockMvc.perform(
                        post("/api/user/post")
                                .with(user("test").roles("ADMIN"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(postDto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("post-create",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())

                        )
                );

    }


    @Test
    @DisplayName("포스트 조회")
    public void findPost() throws Exception {

        mockMvc.perform(
                        RestDocumentationRequestBuilders.get(
                                "/api/all/post/{postId}", 3)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("post-get",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("postId").description("조회할 포스트 id")
                                )
                        )
                );

    }

    @Test
    @DisplayName("포스트 리스트 조회")
    public void postList() throws Exception {

        mockMvc.perform(
                        get("/api/all/post/list")
                                .param("categoryId", "1")
                                .param("page", "0")
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("post-list",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("categoryId").description("조회할 카테고리 Id"),
                                        parameterWithName("page").description("해당 페이지")
                                )
                        )
                );
    }

}