package com.project.gameInfo.repository;

import com.project.gameInfo.domain.Category;
import com.project.gameInfo.domain.GamesCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GamesCategoryRepository extends JpaRepository<GamesCategory, Long> {

    @Query("select new Category(gc.category.id, gc.category.name, gc.category.parentId, gc.category.state) " +
            "from games_category gc " +
            "where gc.games.id = :id")
    List<Category> findCategoryByGamesId(@Param("id") Long id);
}
