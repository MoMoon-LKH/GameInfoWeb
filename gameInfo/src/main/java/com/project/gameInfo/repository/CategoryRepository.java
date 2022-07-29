package com.project.gameInfo.repository;

import com.project.gameInfo.controller.dto.CategoryDto;
import com.project.gameInfo.domain.Category;
import com.project.gameInfo.domain.enums.CategoryState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {


}
