package com.project.gameInfo.repository;


import com.project.gameInfo.controller.dto.GenreDto;
import com.project.gameInfo.domain.Genre;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long>, CustomGenreRepository {



    List<Genre> findAllByNameStartingWithOrderByNameAsc(String name, Pageable pageable);

    @Query("select g from Genre g where g.id in :ids")
    List<Genre> findByIds(@Param("ids") List<Long> ids);
}
