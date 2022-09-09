package com.project.gameInfo.repository;

import com.project.gameInfo.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, CustomPostRepository {


    List<Post> findAllByCategoryIdOrderByCreateDateDesc(@Param("categoryId") Long categoryId);

}
