package com.reach.blog.services;

import java.util.List;

import com.reach.blog.dto.CategoryDTO;

public interface CategoryService {
    List<CategoryDTO> findAll();

    CategoryDTO findById(Long categoryId);

    CategoryDTO create(CategoryDTO categoryDTO);

    CategoryDTO update(Long categoryId, CategoryDTO categoryDTO);

    void delete(Long categoryId);
}
