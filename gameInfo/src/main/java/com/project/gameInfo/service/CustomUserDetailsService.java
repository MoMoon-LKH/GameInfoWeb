package com.project.gameInfo.service;


import com.project.gameInfo.domain.Member;
import com.project.gameInfo.exception.NotFindMemberException;
import com.project.gameInfo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return memberRepository.findOneByMemberId(username)
                .map(m -> createUser(m))
                .orElseThrow(NotFindMemberException::new);
    }


    private User createUser(Member member) {
        List<GrantedAuthority> grantedAuthorities =
                member.getRoleList()
                        .stream().map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new User(member.getMemberId(), member.getPassword(), grantedAuthorities);
    }
}
