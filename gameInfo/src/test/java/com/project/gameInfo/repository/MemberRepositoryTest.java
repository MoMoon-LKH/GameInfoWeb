package com.project.gameInfo.repository;

import com.project.gameInfo.controller.dto.MemberDto;
import com.project.gameInfo.domain.Member;
import com.project.gameInfo.exception.NotFindMemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;



    @Test
    @DisplayName("회원 저장")
    void saveMember() {

        //given
        Member member = generateMember("saveTest");

        //when
        Member savedMember = memberRepository.save(member);

        //then
        assertThat(member).isSameAs(savedMember); // 주소값 비교
        assertThat(member).isEqualTo(savedMember); // 값 비교
        assertThat(savedMember.getId()).isEqualTo(member.getId());

    }


    @Test
    @DisplayName("회원 찾기")
    void findMember() {

        //given
        String txt = "saveTest";
        Member member = generateMember(txt);
        memberRepository.save(member);

        //when
        Member member1 = memberRepository.findOneByMemberId(txt).orElseThrow(NotFindMemberException::new);
        Member member2 = memberRepository.findById(member.getId()).orElseThrow(NotFindMemberException::new);

        //then
        assertThat(member1).isSameAs(member2);
        assertThat(member1.getMemberId()).isEqualTo(member.getMemberId());
        assertThat(member2.getMemberId()).isEqualTo(member.getMemberId());


    }

    @Test
    @DisplayName("회원 삭제")
    void deleteMember() {

        //given
        Member member = generateMember("saveTest");
        memberRepository.save(member);

        //when
        memberRepository.delete(member);

        //then
        assertThrows(NotFindMemberException.class, () ->{
            memberRepository.findOneByMemberId(member.getMemberId()).orElseThrow(NotFindMemberException::new);
        });


    }


    Member generateMember(String mem) {
        return Member.createMember(
                MemberDto.builder()
                        .memberId(mem)
                        .password(mem)
                        .nickname(mem)
                        .roles("USER")
                        .build());
    }

}