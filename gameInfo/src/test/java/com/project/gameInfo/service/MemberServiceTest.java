package com.project.gameInfo.service;

import com.project.gameInfo.controller.dto.MemberDto;
import com.project.gameInfo.domain.Member;
import com.project.gameInfo.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberService memberService;


    @Test
    @DisplayName("멤버 생성")
    void 회원가입() {

        //given
        MemberDto memberDto = initialMemberDto();

        Member member = Member.createMember(memberDto);

        //when
        memberService.save(member);

    }
    
    @Test
    @DisplayName("회원 찾기")
    void 회원찾기() {

        //given
        MemberDto memberDto = initialMemberDto();

        Member member = Member.createMember(memberDto);
        when(memberRepository.save(any())).thenReturn(member);

        memberService.save(member);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(memberRepository.findOneByMemberId(member.getMemberId())).thenReturn(Optional.of(member));

        //when
        Member findMember = memberService.findMemberById(1L);
        Member findMember2 = memberService.findMemberByMemberId(member.getMemberId());

        //then
        assertThat(member.getMemberId()).isEqualTo(findMember.getMemberId());
        assertThat(findMember.getMemberId()).isEqualTo(findMember2.getMemberId());

    }



    MemberDto initialMemberDto() {
        return  MemberDto.builder()
                .memberId("test1")
                .password("test1")
                .nickname("test1")
                .phone("010-1111-1111")
                .email("test@gmail.com").build();

    }

}