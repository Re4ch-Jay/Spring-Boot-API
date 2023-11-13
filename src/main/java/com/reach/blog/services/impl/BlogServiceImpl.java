package com.reach.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reach.blog.dto.BlogCreateDTO;
import com.reach.blog.dto.BlogDTO;
import com.reach.blog.dto.BlogWithCategoryDTO;
import com.reach.blog.dto.BlogWithCommentDTO;
import com.reach.blog.exception.NotFoundException;
import com.reach.blog.mapper.BlogMapper;
import com.reach.blog.models.Blog;
import com.reach.blog.models.Category;
import com.reach.blog.repository.BlogRepository;
import com.reach.blog.repository.CategoryRepository;
import com.reach.blog.services.BlogService;

@Service
public class BlogServiceImpl implements BlogService {

    private BlogRepository blogRepository;
    private CategoryRepository categoryRepository;
    private BlogMapper mapper;

    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository, CategoryRepository categoryRepository, BlogMapper mapper) {
        this.blogRepository = blogRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public List<BlogDTO> findAll() {
        List<Blog> blogs = blogRepository.findAll();

        List<BlogDTO> blogList = blogs.stream().map(b -> mapper.mapToDTO(b)).toList();

        return blogList;
    }

    @Override
    public List<BlogWithCategoryDTO> findAllWithCategory() {
        List<Blog> blogs = blogRepository.findAll();

        List<BlogWithCategoryDTO> blogList = blogs.stream().map(b -> mapper.mapToDTOWithCategory(b)).toList();

        return blogList;
    }

    @Override
    public List<BlogWithCommentDTO> findAllWithComment() {
        List<Blog> blogs = blogRepository.findAll();

        List<BlogWithCommentDTO> blogList = blogs.stream().map(b -> mapper.mapToDTOWithComment(b)).toList();

        return blogList;
    }

    @Override
    public BlogDTO findById(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new NotFoundException("Blog could not be found"));

        return mapper.mapToDTO(blog);
    }

    @Override
    public BlogWithCategoryDTO findByIdWithCategory(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new NotFoundException("Blog could not be found"));

        return mapper.mapToDTOWithCategory(blog);
    }

    @Override
    public BlogWithCommentDTO findByIdWithComment(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new NotFoundException("Blog could not be found"));

        return mapper.mapToDTOWithComment(blog);
    }

    @Override
    public void create(BlogCreateDTO blogDTO) {

        Blog blog = new Blog();

        blog.setTitle(blogDTO.getTitle());
        blog.setDescription(blogDTO.getDescription());
        blog.setCreatedAt(new Date());
        blog.setUpdatedAt(new Date());

        List<Category> categories = blogDTO.getCategoryIds().stream()
                .map(categoryId -> categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new NotFoundException("Category with id " + categoryId + " not found")))
                .collect(Collectors.toList());

        blog.setCategories(categories);

        blogRepository.save(blog);
    }

    @Override
    public BlogDTO update(Long id, BlogDTO blogDTO) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new NotFoundException("Blog could not be found"));
        blog.setTitle(blogDTO.getTitle());
        blog.setDescription(blogDTO.getDescription());
        blog.setUpdatedAt(new Date());

        blogRepository.save(blog);
        return mapper.mapToDTO(blog);
    }

    @Override
    public void delete(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new NotFoundException("Blog could not be found"));
        blogRepository.delete(blog);
    }

}