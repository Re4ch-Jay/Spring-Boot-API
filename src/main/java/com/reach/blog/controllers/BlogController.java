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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.reach.blog.dto.BlogCreateDTO;
import com.reach.blog.dto.BlogDTO;
import com.reach.blog.response.ApiResponse;
import com.reach.blog.services.BlogService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class BlogController {

    private BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/blogs")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getBlogs(@RequestParam(name = "include", required = false) String include,
            @RequestParam(name = "search", required = false) String query) {
        List<?> blogs;

        if ("categories".equals(include)) {
            blogs = blogService.findAllWithCategory();
        } else if ("comments".equals(include)) {
            blogs = blogService.findAllWithComment();
        } else {
            if (query != null && !query.isEmpty()) {
                blogs = blogService.findAllWithQuery(query);
            } else {
                blogs = blogService.findAll();
            }
        }

        return ResponseEntity.ok(blogs);
    }

    @GetMapping("/blogs/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<BlogDTO>> getBlog(@PathVariable(name = "id") Long id,
            @RequestParam(name = "include", required = false) String include) {

        BlogDTO blog;

        if ("categories".equals(include)) {
            blog = blogService.findByIdWithCategory(id);
        } else if ("comments".equals(include)) {

            blog = blogService.findByIdWithComment(id);
        } else {
            blog = blogService.findById(id);
        }

        ApiResponse<BlogDTO> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Blog find successfully");
        apiResponse.setData(blog);
        apiResponse.setStatusCode(HttpStatus.OK.value());

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/blogs")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createBlog(@Valid @RequestBody BlogCreateDTO blogDTO) {

        blogService.create(blogDTO);

        return new ResponseEntity<>("Blog Created", HttpStatus.CREATED);
    }

    @PutMapping("/blogs/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateBlog(@PathVariable(name = "id") Long id,
            @Valid @RequestBody BlogDTO blogDTO) {

        BlogDTO updateBlog = blogService.update(id, blogDTO);

        ApiResponse<BlogDTO> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Blog updated successfully");
        apiResponse.setData(updateBlog);
        apiResponse.setStatusCode(HttpStatus.OK.value());

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/blogs/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteBlog(@PathVariable(name = "id") Long id) {
        blogService.delete(id);
        return new ResponseEntity<>("Delete Successfully", HttpStatus.OK);
    }
}
