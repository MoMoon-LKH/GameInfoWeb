package com.project.gameInfo.repository;

import com.project.gameInfo.controller.dto.PostDto;
import com.project.gameInfo.controller.dto.PostListDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomPostRepository {

    List<PostListDto> findAllByCategoryIdAndGamePage(Long categoryId, Long gameId, Pageable pageable);
}
