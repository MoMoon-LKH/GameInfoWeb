package com.project.gameInfo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gameInfo.controller.dto.CommentDto;
import com.project.gameInfo.controller.dto.CreateCommentDto;
import com.project.gameInfo.controller.dto.UpdateCommentDto;
import org.hibernate.sql.Update;
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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                                        fieldWithPath("nickname").description("작성자 닉네임"),
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
    @DisplayName("댓글 업데이트")
    @Transactional
    public void updateComment() throws Exception {

        UpdateCommentDto commentDto = UpdateCommentDto.builder()
                .id(22L)
                .content("update content")
                .build();


        mockMvc.perform(
                patch("/api/user/comment")
                        .content(objectMapper.writeValueAsString(commentDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("test").roles("ADMIN"))
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("comment-update",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("id").description("수정할 댓글 id"),
                                        fieldWithPath("content").description("수정할 댓글 내용")
                                ),
                                responseFields(
                                        fieldWithPath("id").description("id"),
                                        fieldWithPath("memberId").description("작성자 id"),
                                        fieldWithPath("nickname").description("작성자 닉네임"),
                                        fieldWithPath("parentNickname").description("부모 닉네임").optional(),
                                        fieldWithPath("content").description("댓글 내용"),
                                        fieldWithPath("likes").description("좋아요 수"),
                                        fieldWithPath("unlikes").description("싫어요 수"),
                                        fieldWithPath("status").description("삭제 여부"),
                                        fieldWithPath("groupNum").description("댓글 그룹 아이디"),
                                        fieldWithPath("groupOrder").description("댓글 그룹 순서")
                                )
                                )
                );
                

    }


    @Test
    @DisplayName("댓글 삭제")
    @Transactional
    public void deleteComment() throws Exception {

        mockMvc.perform(
                delete("/api/user/comment")
                        .param("commentId", "22")
                        .with(user("test").roles("ADMIN"))
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("comment-delete",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("commentId").description("삭제할 댓글 id")
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
                                preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("postId").description("해당 포스트 id"),
                                parameterWithName("page").description("불러올 페이지")
                        ),
                        responseFields(
                                fieldWithPath("[].id").description("id"),
                                fieldWithPath("[].parentNickname").description("부모 닉네임").optional(),
                                fieldWithPath("[].memberId").description("작성자 id"),
                                fieldWithPath("[].nickname").description("작성자 닉네임"),
                                fieldWithPath("[].content").description("작성 댓글"),
                                fieldWithPath("[].status").description("삭제 여부"),
                                fieldWithPath("[].likes").description("좋아요 수"),
                                fieldWithPath("[].unlikes").description("싫어요 수"),
                                fieldWithPath("[].groupNum").description("댓글 그룹 아이디"),
                                fieldWithPath("[].groupOrder").description("댓글 그룹 순서")  
                        )
                        )
                );

    }

    @Test
    @DisplayName("댓글 좋아요 기능")
    @Transactional
    public void commentLike() throws Exception {

        mockMvc.perform(
                        post("/api/user/comment/like")
                                .param("commentId", "22")
                                .with(user("test").roles("ADMIN"))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("comment-like",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("commentId").description("해당 댓글 id")
                                ),
                                responseFields(
                                        fieldWithPath("name").description("버튼 타입 (Like | dislike)"),
                                        fieldWithPath("type").description("동작 타입 (create | delete)"),
                                        fieldWithPath("message").description("메시지")
                                )
                        )
                );
    }

    @Test
    @DisplayName("댓글 싫어요 기능")
    @Transactional
    public void commentUnlike() throws Exception {

        mockMvc.perform(
                        post("/api/user/comment/unlike")
                                .param("commentId", "22")
                                .with(user("test").roles("ADMIN"))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("comment-dislike",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("commentId").description("해당 댓글 id")
                                ),
                                requestParameters(
                                        parameterWithName("commentId").description("해당 댓글 id")
                                ),
                                responseFields(
                                        fieldWithPath("name").description("버튼 타입 (Like | dislike)"),
                                        fieldWithPath("type").description("동작 타입 (create | delete)"),
                                        fieldWithPath("message").description("메시지")
                                )
                        )
                );
    }




}