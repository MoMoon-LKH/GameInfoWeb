package com.project.gameInfo.repository;

import com.project.gameInfo.config.TestConfig;
import com.project.gameInfo.controller.dto.GenreDto;
import com.project.gameInfo.domain.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@Import(TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;


    @Test
    @DisplayName("장르 추가")
    public void createGenre() {

        //given
        Genre genre = new Genre("테스트");

        //when
        Genre save = genreRepository.save(genre);

        //then
        assertThat(genre).isSameAs(save);
        assertThat(genre.getName()).isEqualTo(save.getName());

    }

    @Test
    @DisplayName("장르 가져오기(Pageable)")
    public void getGenreByPageable() {

        //given
        Pageable pageable = PageRequest.of(0, 2);
        Pageable pageable2 = PageRequest.of(0, 1);
        Pageable pageable3 = PageRequest.of(1, 10);
        Genre genre1 = new Genre("테스트");
        Genre genre2 = new Genre("테스트2");
        genreRepository.save(genre1);
        genreRepository.save(genre2);

        //when
        List<GenreDto> list1 = genreRepository.findAllByPage(pageable);
        List<GenreDto> list2 = genreRepository.findAllByPage(pageable2);
        List<GenreDto> list3 = genreRepository.findAllByPage(pageable3);

        //then
        assertThat(list1.size()).isEqualTo(2);
        assertThat(list2.size()).isEqualTo(1);
        assertThat(list3.size()).isEqualTo(0);

    }



}