package com.project.gameInfo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gameInfo.controller.dto.JoinDto;
import com.project.gameInfo.controller.dto.MemberDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class MemberControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @Transactional
    @DisplayName("회원가입")
    public void createMember() throws Exception {

        JoinDto memberDto = JoinDto.builder()
                .memberId("Test2")
                .password("Test2")
                .nickname("SignUpNickname")
                .name("가입테스트")
                .phone("010-2222-2222")
                .email("member@gmail.com")
                .build();


        mockMvc.perform(
                        post("/api/member/join")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(memberDto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("member-signup",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("id").description("id"),
                                        fieldWithPath("memberId").description("로그인 아이디"),
                                        fieldWithPath("name").description("이름"),
                                        fieldWithPath("nickname").description("닉네임"),
                                        fieldWithPath("email").description("이메일"),
                                        fieldWithPath("phone").description("휴대전화"),
                                        fieldWithPath("roles").description("권한")
                                )
                        )
                );
    }

    @Test
    @DisplayName("Member 단일 조회")
    public void findMember() throws Exception{

        mockMvc.perform(
                        get("/api/member/info")
                                .param("id", "1")
                                .with(user("test").roles("USER"))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("member-get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("id").description("조회할 id")
                        ),
                        responseFields(
                                fieldWithPath("id").description("id"),
                                fieldWithPath("memberId").description("로그인 아이디"),
                                fieldWithPath("name").description("이름"),
                                fieldWithPath("nickname").description("닉네임"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("phone").description("휴대전화"),
                                fieldWithPath("roles").description("권한")
                        )
                ));
    }

    @Test
    @DisplayName("Member Id 중복 조회")
    public void duplicateMemberId() throws Exception {


        mockMvc.perform(
                get("/api/member/join/duplicate")
                        .param("memberId", "test1")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("member-duplicate",
                                requestParameters(
                                        parameterWithName("memberId").description("조회할 아이디")
                                ))
                );
    }


}