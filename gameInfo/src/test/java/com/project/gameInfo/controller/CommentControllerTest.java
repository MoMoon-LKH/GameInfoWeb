package com.project.gameInfo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gameInfo.controller.dto.CreateCommentDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @DisplayName("댓글 생성")
    @Transactional
    public void createComment() throws Exception{

        CreateCommentDto commentDto = CreateCommentDto.builder()
                .parentId(22L)
                .content("test1-6")
                .memberId(1L)
                .postId(2L).build();

        mockMvc.perform(
                        post("/api/user/comment")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(commentDto))
                                .with(user("test").roles("USER"))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("comment-create",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("content").description("댓글 내용"),
                                        fieldWithPath("parentId").description("답글할 댓글 id"),
                                        fieldWithPath("memberId").description("작성자 id"),
                                        fieldWithPath("postId").description("포스트 id")
                                ),
                                responseFields(
                                        fieldWithPath("id").description("id"),
                                        fieldWithPath("parentNickname").description("부모 닉네임"),
                                        fieldWithPath("memberId").description("작성자 id"),
                                        fieldWithPath("nickname").description("작성자 id"),
                                        fieldWithPath("content").description("작성 댓글"),
                                        fieldWithPath("status").description("삭제 여부"),
                                        fieldWithPath("likes").description("좋아요 수"),
                                        fieldWithPath("unlikes").description("싫어요 수"),
                                        fieldWithPath("groupNum").description("댓글 그룹 아이디"),
                                        fieldWithPath("groupOrder").description("댓글 그룹 순서")
                                )
                        )
                );
    }

    @Test
    @DisplayName("댓글 리스트 조회")
    public void commentList() throws Exception {

        mockMvc.perform(
                        get("/api/all/comment")
                                .param("postId", "2")
                                .param("page","0")
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("comment-list",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );

    }


}