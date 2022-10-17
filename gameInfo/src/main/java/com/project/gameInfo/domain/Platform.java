package com.project.gameInfo.domain;


import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Platform {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String name;

    @OneToMany(mappedBy = "platform")
    private List<GamesPlatform> gamesPlatforms = new ArrayList<>();


    public Platform(String name) {
        this.name = name;
    }

    public Platform() {

    }

    public void updateName(String name) {
        this.name = name;
    }
}
