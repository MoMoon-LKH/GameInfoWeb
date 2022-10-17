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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

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
                .memberId(11L)
                .categoryId(1L)
                .gameId(1L)
                .build();


        mockMvc.perform(
                        post("/api/user/post")
                                .with(user("test2").roles("USER"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(postDto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("post-create",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("title").description("제목"),
                                        fieldWithPath("content").description("내용"),
                                        fieldWithPath("memberId").description("작성자 id"),
                                        fieldWithPath("categoryId").description("카테고리 id"),
                                        fieldWithPath("gameId").description("게임 id")
                                ),
                                responseFields(
                                        fieldWithPath("id").description("id"),
                                        fieldWithPath("title").description("제목"),
                                        fieldWithPath("content").description("내용"),
                                        fieldWithPath("createDate").description("생성일자"),
                                        fieldWithPath("memberId").description("작성자 id")
                                )

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
                                ),
                                responseFields(
                                        subsectionWithPath("post").description("post 데이터 객체"),
                                        subsectionWithPath("category").description("category 데이터 객체"),
                                        subsectionWithPath("member").description("member 데이터 객체"),
                                        subsectionWithPath("game").description("game 데이터 객체")
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
                                ),
                                responseFields(
                                        subsectionWithPath("posts").description("포스트 리스트"),
                                        fieldWithPath("posts[].id").description("id"),
                                        fieldWithPath("posts[].title").description("제목"),
                                        fieldWithPath("posts[].view").description("조회수"),
                                        fieldWithPath("posts[].memberId").description("작성자 id"),
                                        fieldWithPath("posts[].nickname").description("작성자 닉네임"),
                                        fieldWithPath("posts[].commentCount").description("댓글 수"),
                                        fieldWithPath("posts[].createDate").description("작성일자"),
                                        subsectionWithPath("total").description("포스트 총 개수")
                                )
                        )
                );
    }

    @Test
    @Transactional
    @DisplayName("포스트 수정")
    public void updatePost() throws Exception{

        Long id = createPostId();
        PostDto postDto = PostDto.builder()
                .id(id)
                .title("update test")
                .content("update test")
                .memberId(1L)
                .build();

        mockMvc.perform(
                        put("/api/user/post")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(postDto))
                                .with(user("test").roles("ADMIN"))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("post-update",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("id").description("수정할 id"),
                                        fieldWithPath("title").description("수정한 제목"),
                                        fieldWithPath("content").description("수정한 내용"),
                                        fieldWithPath("memberId").description("작성자 id"),
                                        fieldWithPath("createDate").description("작성일자")
                                )
                        )
                );



    }


    @Test
    @Transactional
    @DisplayName("포스트 삭제")
    public void deletePost() throws Exception{

        Long id = createPostId();

        mockMvc.perform(
                        delete("/api/user/post")
                                .param("id", id.toString())
                                .with(user("test").roles("ADMIN"))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("post-delete",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("id").description("삭제할 id")
                                ))
                );
    }



    protected Long createPostId() throws Exception{

        CreatePostDto postDto = CreatePostDto.builder()
                .title("post test")
                .content("content")
                .memberId(1L)
                .categoryId(1L)
                .gameId(1L)
                .build();

        MvcResult mvcResult = mockMvc.perform(
                post("/api/user/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto))
                        .with(user("test").roles("ADMIN"))


        ).andReturn();

        Map<String, Object> map = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Map.class);


        return Long.parseLong(map.get("id").toString());
    }

}