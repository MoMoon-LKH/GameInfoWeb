package com.project.gameInfo.controller;

import com.project.gameInfo.controller.dto.JoinDto;
import com.project.gameInfo.controller.dto.MemberDto;
import com.project.gameInfo.domain.Member;
import com.project.gameInfo.exception.DuplicateMemberIdException;
import com.project.gameInfo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
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


            return ResponseEntity.ok(convertMemberDto(member));
        }
    }

    @GetMapping("/join/duplicate")
    public ResponseEntity<?> duplicateMemberId(@RequestParam String memberId) {
        return ResponseEntity.ok(memberService.duplicateMemberId(memberId));
    }


    @GetMapping("/info")
    @RolesAllowed({"USER", "ADMIN", "REVIEW"})
    public ResponseEntity<?> findMember(@RequestParam Long id) {
        Member member = memberService.findMemberById(id);

        return ResponseEntity.ok(convertMemberDto(member));
    }



    public MemberDto convertMemberDto(Member member) {

        return MemberDto.builder()
                .id(member.getId())
                .memberId(member.getMemberId())
                .name(member.getName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .nickname(member.getNickname())
                .roles(member.getRoles())
                .build();
    }
}
