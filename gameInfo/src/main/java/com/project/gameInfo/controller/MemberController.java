package com.project.gameInfo.controller;

import com.project.gameInfo.controller.dto.MemberDto;
import com.project.gameInfo.domain.Member;
import com.project.gameInfo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/join")
    public ResponseEntity<?> joinMember(@RequestBody MemberDto memberDto) {
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        Member member = Member.createMember(memberDto);

        Long memberId = memberService.save(member);

        return ResponseEntity.ok("가입 아이디: " + memberId);
    }

    @GetMapping("/info")
    public ResponseEntity<?> findMember(@RequestParam Long id) {
        Member member = memberService.findMemberById(id);

        return ResponseEntity.ok(convertMemberDto(member));
    }


    public MemberDto convertMemberDto(Member member) {

        return MemberDto.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .status(member.getStatus().toString())
                .build();
    }
}
