package com.project.gameInfo.service;

import com.project.gameInfo.domain.Category;
import com.project.gameInfo.domain.Games;
import com.project.gameInfo.domain.GamesCategory;
import com.project.gameInfo.repository.CategoryRepository;
import com.project.gameInfo.repository.GamesCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final GamesCategoryRepository gamesCategoryRepository;

    @Transactional
    public Long save(Category category) {
        return categoryRepository.save(category).getId();
    }

    @Transactional
    public void updateName(Category category, String name) {
        category.updateName(name);
    }

    @Transactional
    public Long saveGamesCategory(Games games, Category category) {
        return gamesCategoryRepository.save(new GamesCategory(games, category)).getId();
    }

    @Transactional
    public void deleteState(Category category) {
        category.updateDeleteState();
    }

    public Category findByCategoryId(Long id) {
        return categoryRepository.findById(id).orElseThrow(NoSuchFieldError::new);
    }

    public List<Category> findAllByGamesId(Long id) {
        return gamesCategoryRepository.findCategoryByGamesId(id);
    }

}
