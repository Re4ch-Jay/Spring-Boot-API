package com.reach.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reach.blog.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBlogId(Long blogId);
}
