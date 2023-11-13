package com.reach.blog.services;

import java.util.List;

import com.reach.blog.dto.BlogCreateDTO;
import com.reach.blog.dto.BlogDTO;
import com.reach.blog.dto.BlogWithCategoryDTO;
import com.reach.blog.dto.BlogWithCommentDTO;

public interface BlogService {
    List<BlogDTO> findAll();

    List<BlogWithCategoryDTO> findAllWithCategory();

    List<BlogWithCommentDTO> findAllWithComment();

    BlogDTO findById(Long id);

    BlogWithCategoryDTO findByIdWithCategory(Long id);

    BlogWithCommentDTO findByIdWithComment(Long id);

    void create(BlogCreateDTO blogDTO);

    BlogDTO update(Long id, BlogDTO blogDTO);

    void delete(Long id);
}
