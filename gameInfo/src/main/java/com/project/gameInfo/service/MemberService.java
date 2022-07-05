package com.project.gameInfo.service;

import com.project.gameInfo.domain.Member;
import com.project.gameInfo.exception.NotFindMemberException;
import com.project.gameInfo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long save(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

    public Member findMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(NotFindMemberException::new);
    }

    public boolean duplicateMemberId(String memberId) {
        return memberRepository.duplicateId(memberId) > 0;
    }
}
