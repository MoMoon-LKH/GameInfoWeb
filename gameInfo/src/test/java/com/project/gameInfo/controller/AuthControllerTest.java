package com.project.gameInfo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gameInfo.controller.dto.LoginDto;
import com.project.gameInfo.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MemberService memberService;


    @Test
    @DisplayName("로그인")
    public void login() throws Exception{

        LoginDto loginDto = LoginDto.builder()
                .memberId("test")
                .password("test")
                .build();

        mockMvc.perform(
                        post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginDto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document(
                                "auth-login",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("memberId").description("로그인 아이디"),
                                        fieldWithPath("password").description("비밀번호")
                                ),
                                responseFields(
                                        fieldWithPath("id").description("id"),
                                        fieldWithPath("nickname").description("닉네임"),
                                        fieldWithPath("message").description("로그인 성공 여부 메시지"),
                                        fieldWithPath("accessToken").description("인증이 필요한 서비스에 사용되는 토큰")
                                )
                        )

                );
    }

    @Test
    @DisplayName("재인증")
    public void reAuthorize() throws Exception{

        LoginDto loginDto = LoginDto.builder()
                .memberId("test")
                .password("test")
                .build();

        MvcResult mvcResult =  mockMvc.perform(
                        post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginDto))
                )
                .andExpect(status().isOk())
                        .andReturn();

       Map<String, Object> map = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Map.class);


        mockMvc.perform(
                post("/api/auth/re-access")
                        .param("access", map.get("accessToken").toString())
                        .cookie(mvcResult.getResponse().getCookie("gameInfo"))
        ).andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("auth-reaccess",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseHeaders(
                                        headerWithName("Authorization").description("재발급 받은 Access 토큰")
                                )
                        )
                );

    }

    @Test
    @DisplayName("로그아웃")
    public void logout() throws Exception{

        mockMvc.perform(
                post("/api/user/logout")
                        .with(user("test").roles("USER"))
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("auth-logout")
                );
    }

    @Test
    @DisplayName("권한 확인 - Manager")
    public void getAuthority() throws Exception{

        mockMvc.perform(
                get("/api/user/is-manage")
                        .with(user("test").roles("ADMIN"))
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("auth-getAuthority")
                );
    }




}