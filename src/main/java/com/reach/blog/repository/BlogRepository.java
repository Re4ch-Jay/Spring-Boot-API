package com.reach.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reach.blog.models.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    
}
