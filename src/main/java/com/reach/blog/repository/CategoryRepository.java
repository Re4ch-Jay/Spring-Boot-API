package com.reach.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reach.blog.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByBlogId(Long categoryId);
}
