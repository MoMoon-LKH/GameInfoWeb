package com.project.gameInfo.repository;


import com.project.gameInfo.controller.dto.PlatformDto;
import com.project.gameInfo.domain.QPlatform;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomPlatformRepositoryImpl implements CustomPlatformRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PlatformDto> findAllByPage(Pageable pageable) {

        QPlatform platform = QPlatform.platform;

        return jpaQueryFactory.select(Projections.bean(PlatformDto.class,
                        platform.id.as("id"),
                        platform.name.as("name")))
                .from(platform)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }
}
