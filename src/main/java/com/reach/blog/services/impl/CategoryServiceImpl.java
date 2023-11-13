package com.reach.blog.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reach.blog.dto.CategoryDTO;
import com.reach.blog.exception.NotFoundException;
import com.reach.blog.mapper.CategoryMapper;
import com.reach.blog.models.Category;
import com.reach.blog.repository.CategoryRepository;
import com.reach.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryMapper mapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryList = categories.stream().map(t -> mapper.mapToDTO(t)).toList();

        return categoryList;
    }

    @Override
    public CategoryDTO findById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category could not be found"));
        return mapper.mapToDTO(category);
    }

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {

        Category category = new Category();

        category.setName(categoryDTO.getName());
        categoryRepository.save(category);

        CategoryDTO newCategory = new CategoryDTO();

        newCategory.setId(category.getId());
        newCategory.setName(category.getName());

        return mapper.mapToDTO(category);
    }

    @Override
    public CategoryDTO update(Long categoryId, CategoryDTO categoryDTO) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category could not be found"));

        category.setName(categoryDTO.getName());
        categoryRepository.save(category);

        CategoryDTO newCategory = new CategoryDTO();

        newCategory.setId(category.getId());
        newCategory.setName(category.getName());

        return newCategory;
    }

    @Override
    public void delete(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category could not be found"));
        categoryRepository.delete(category);
    }

}
