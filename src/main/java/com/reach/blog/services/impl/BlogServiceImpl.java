package com.reach.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.reach.blog.dto.BlogCreateDTO;
import com.reach.blog.dto.BlogDTO;
import com.reach.blog.dto.BlogWithCategoryDTO;
import com.reach.blog.dto.BlogWithCommentDTO;
import com.reach.blog.exception.NotFoundException;
import com.reach.blog.mapper.BlogMapper;
import com.reach.blog.models.Blog;
import com.reach.blog.models.Category;
import com.reach.blog.models.User;
import com.reach.blog.repository.BlogRepository;
import com.reach.blog.repository.CategoryRepository;
import com.reach.blog.repository.UserRepository;
import com.reach.blog.services.BlogService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BlogServiceImpl implements BlogService {

    private BlogRepository blogRepository;
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;
    private BlogMapper mapper;

    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository, CategoryRepository categoryRepository,
            UserRepository userRepository, BlogMapper mapper) {
        this.blogRepository = blogRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public List<BlogDTO> findAll() {
        List<Blog> blogs = blogRepository.findAll();

        List<BlogDTO> blogList = blogs.stream().map(b -> mapper.mapToDTO(b)).toList();

        return blogList;
    }

    @Override
    public List<BlogDTO> findAllWithQuery(String query) {
        List<Blog> blogs = blogRepository.findByTitleLike(query);

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

        User owner = userRepository.findByUsername(getCurrentUser())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

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
        blog.setUser(owner);

        blogRepository.save(blog);
    }

    @Override
    public BlogDTO update(Long id, BlogDTO blogDTO) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new NotFoundException("Blog could not be found"));
        this.authorize(blog);
        blog.setTitle(blogDTO.getTitle());
        blog.setDescription(blogDTO.getDescription());
        blog.setUpdatedAt(new Date());

        blogRepository.save(blog);
        return mapper.mapToDTO(blog);
    }

    @Override
    public void delete(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new NotFoundException("Blog could not be found"));

        // User doesn't have permission
        this.authorize(blog);
        // User has permission to delete the blog post
        blogRepository.delete(blog);
    }

    /**
     * Access denied if the user is not the owner
     * 
     * @param blog
     */
    private void authorize(Blog blog) {
        if (!blog.getUser().getUsername().equals(this.getCurrentUser())) {
            // User doesn't have permission
            throw new AccessDeniedException("You do not have permission to delete this blog post");
        }
    }

    /**
     * Get the current authenticate user
     */
    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        return currentUsername;
    }
}