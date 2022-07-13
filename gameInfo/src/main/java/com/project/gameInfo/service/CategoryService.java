package com.project.gameInfo.service;

import com.project.gameInfo.domain.Category;
import com.project.gameInfo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    @Transactional
    public Long save(Category category) {
        return categoryRepository.save(category).getId();
    }

    @Transactional
    public void updateName(Category category, String name) {
        category.updateName(name);
    }

}
