package com.project.gameInfo.service;

import com.project.gameInfo.config.TestConfig;
import com.project.gameInfo.controller.dto.GenreDto;
import com.project.gameInfo.domain.Genre;
import com.project.gameInfo.repository.GenreRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreService genreService;


    @Test
    @DisplayName("Service - 장르 저장")
    public void createGenre() {

        //given
        Genre genre = new Genre("테스트");
        when(genreRepository.save(genre)).thenReturn(genre);

        //when
        genreService.save(genre);

    }
    
    @Test
    @DisplayName("Service - 장르 id로 찾기")
    public void findById() {
        //given
        Genre genre = new Genre("테스트");
        when(genreRepository.save(genre)).thenReturn(genre);

        Long save = genreService.save(genre);
        when(genreRepository.findById(save)).thenReturn(Optional.of(genre));

        //when
        Genre find = genreService.getById(save);

        //then
        assertThat(genre).isSameAs(find);


    }

    @Test
    @DisplayName("Service - 장르 리스트 (Pageable)")
    public void findAllByPageable(){
        //given
        Pageable pageable = PageRequest.of(0, 10);
        Pageable pageable2 = PageRequest.of(1, 10);
        Genre genre1 = new Genre("테스트1");
        when(genreRepository.save(genre1)).thenReturn(genre1);
        Genre genre2 = new Genre("테스트2");
        when(genreRepository.save(genre2)).thenReturn(genre2);

        List<GenreDto> genres = new ArrayList<>();
        genres.add(convertDto(genre1));
        genres.add(convertDto(genre2));

        genreService.save(genre1);
        genreService.save(genre2);
        when(genreRepository.findAllByPage(pageable)).thenReturn(genres);
        when(genreRepository.findAllByPage(pageable2)).thenReturn(new ArrayList<>());

        //when
        List<GenreDto> list1 = genreService.getListByPage(pageable);
        List<GenreDto> list2 = genreService.getListByPage(pageable2);

        //then
        assertThat(list1.size()).isEqualTo(2);
        assertThat(list2.size()).isEqualTo(0);
        assertThat(list1.get(0).getName()).isEqualTo(genre1.getName());

    }


    public GenreDto convertDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }
}