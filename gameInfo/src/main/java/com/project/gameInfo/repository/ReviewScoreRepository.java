package com.project.gameInfo.repository;

import com.project.gameInfo.domain.ReviewScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewScoreRepository  extends JpaRepository<ReviewScore, Long> {

    @Query("select avg(rs.score) from review_score rs " +
            "where rs.games.id = :id")
    Double findAvgByGamesId(@Param("id") Long id);
}
