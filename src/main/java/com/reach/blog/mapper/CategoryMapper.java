package com.reach.blog.mapper;

import org.springframework.stereotype.Component;

import com.reach.blog.dto.CategoryDTO;
import com.reach.blog.models.Category;

@Component
public class CategoryMapper extends Mapper {

    public CategoryDTO mapToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

}
