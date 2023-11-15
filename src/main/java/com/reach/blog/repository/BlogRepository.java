package com.reach.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reach.blog.models.Blog;
import java.util.List;


public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByTitleLike(String title);
}
