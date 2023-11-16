package com.reach.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reach.blog.dto.CategoryDTO;
import com.reach.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> createComment(@Valid @RequestBody CategoryDTO CategoryDTO) {
        return ResponseEntity.ok(categoryService.create(CategoryDTO));
    }

    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateComment(@PathVariable(name = "categoryId") Long categoryId,
            @Valid @RequestBody CategoryDTO CategoryDTO) {
        return ResponseEntity.ok(categoryService.update(categoryId, CategoryDTO));
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<?> deleteComment(Long categoryId) {
        categoryService.delete(categoryId);
        return new ResponseEntity<>("Category deleted", HttpStatus.OK);
    }
}
