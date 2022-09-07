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
                .orderBy(platform.name.asc())
                .fetch();
    }

    @Override
    public List<PlatformDto> findAllBySearch(String search, Pageable pageable) {
        QPlatform platform = QPlatform.platform;

        return jpaQueryFactory.select(Projections.bean(PlatformDto.class,
                        platform.id.as("id"),
                        platform.name.as("name")
                )).from(platform)
                .where(platform.name.startsWith(search))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    @Override
    public List<PlatformDto> findAllDto() {
        QPlatform platform = QPlatform.platform;

        return jpaQueryFactory.select(Projections.bean(PlatformDto.class,
                        platform.id.as("id"),
                        platform.name.as("name")))
                .from(platform)
                .orderBy(platform.name.asc())
                .fetch();
    }

    @Override
    public List<PlatformDto> findAllDtoBySearch(String search) {
        QPlatform platform = QPlatform.platform;

        return jpaQueryFactory.select(Projections.bean(PlatformDto.class,
                        platform.id.as("id"),
                        platform.name.as("name")))
                .from(platform)
                .where(platform.name.startsWith(search))
                .orderBy(platform.name.asc())
                .fetch();
    }
}
