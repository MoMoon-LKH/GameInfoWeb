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


    @Column(name = "create_date")
    private Date createDate;

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;


    private RefreshToken(String refreshToken,  Member member) {
        this.refreshToken = refreshToken;
        this.createDate = new Date();
        this.member = member;
    }

    public RefreshToken() {

    }

    public static RefreshToken createRefreshToken(String refreshToken,  Member member) {
        return new RefreshToken(refreshToken, member);
    }

    public void update(String refreshToken) {
        this.refreshToken = refreshToken;
        this.createDate = new Date();
    }
}
