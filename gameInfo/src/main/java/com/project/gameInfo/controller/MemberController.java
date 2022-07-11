package com.project.gameInfo.controller;

import com.project.gameInfo.controller.dto.MemberDto;
import com.project.gameInfo.domain.Member;
import com.project.gameInfo.exception.DuplicateMemberIdException;
import com.project.gameInfo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/join")
    public ResponseEntity<?> joinMember(@RequestBody MemberDto memberDto) {

        Map<String, Object> joinMem = new HashMap<>();

        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberDto.setRoles("USER");

        if (memberService.duplicateMemberId(memberDto.getMemberId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DuplicateMemberIdException());
        } else {

            Member join = Member.createMember(memberDto);

            Long memberId = memberService.save(join);
            Member member = memberService.findMemberById(memberId);

            joinMem.put("아이디", member.getId());
            joinMem.put("멤버 아이디", member.getMemberId());
            joinMem.put("닉네임", member.getNickname());
            joinMem.put("이메일", member.getEmail());

            return ResponseEntity.ok(joinMem);
        }
    }

    @GetMapping("/join/duplicate")
    public ResponseEntity<?> duplicateMemberId(@RequestParam String memberId) {
        return ResponseEntity.ok(memberService.duplicateMemberId(memberId));
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
