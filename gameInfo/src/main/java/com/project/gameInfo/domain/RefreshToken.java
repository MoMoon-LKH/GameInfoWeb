package com.project.gameInfo.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "create_date")
    private Date createDate;

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;


    private RefreshToken(String refreshToken, String accessToken, Member member) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.createDate = new Date();
        this.member = member;
    }

    public RefreshToken() {

    }

    public static RefreshToken createRefreshToken(String refreshToken, String accessToken, Member member) {
        return new RefreshToken(refreshToken, accessToken, member);
    }
}
