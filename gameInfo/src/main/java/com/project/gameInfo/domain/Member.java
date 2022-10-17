package com.project.gameInfo.domain;

import com.project.gameInfo.controller.dto.MemberDto;
import com.project.gameInfo.domain.enums.MemberStatus;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", unique = true, length = 50)
    private String memberId;

    @Column(length = 50)
    private String password;

    @Column(length = 15)
    private String name;

    @Column(length = 20)
    private String nickname;

    @Column(length = 15)
    private String phone;

    @Column(length = 50)
    private String email;

    @Column(name = "create_date")
    private Date creatDate;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "last_connect_date")
    private Date lastConnectDate;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    private String roles;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<CommentLike> commentLikes = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<CommentDislike> commentUnlikes = new ArrayList<>();



    public Member() {

    }

    private Member(MemberDto memberDto) {
        this.memberId = memberDto.getMemberId();
        this.password = memberDto.getPassword();
        this.name = memberDto.getName();
        this.nickname = memberDto.getNickname();
        this.phone = memberDto.getPhone();
        this.email = memberDto.getEmail();
        this.creatDate = new Date();
        this.updateDate = new Date();
        this.roles = memberDto.getRoles();
        this.status = MemberStatus.ALIVE;
    }

    public static Member createMember(MemberDto memberDto) {
        return new Member(memberDto);
    }


    public List<String> getRoleList() {
        return Arrays.asList(this.roles.split(","));
    }

}
