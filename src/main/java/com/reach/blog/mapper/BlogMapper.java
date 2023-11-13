package com.reach.blog.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.reach.blog.dto.BlogDTO;
import com.reach.blog.dto.BlogWithCategoryDTO;
import com.reach.blog.dto.BlogWithCommentDTO;
import com.reach.blog.dto.CategoryDTO;
import com.reach.blog.dto.CommentDTO;
import com.reach.blog.models.Blog;
import com.reach.blog.models.Category;
import com.reach.blog.models.Comment;

@Component
public class BlogMapper extends Mapper {
    public BlogDTO mapToDTO(Blog blog) {
        BlogDTO blogDTO = new BlogDTO();
        blogDTO.setId(blog.getId());
        blogDTO.setTitle(blog.getTitle());
        blogDTO.setDescription(blog.getDescription());
        blogDTO.setCreatedAt(blog.getCreatedAt());
        blogDTO.setUpdatedAt(blog.getUpdatedAt());
        return blogDTO;
    }

    public BlogWithCategoryDTO mapToDTOWithCategory(Blog blog) {
        BlogWithCategoryDTO blogDTO = new BlogWithCategoryDTO();
        blogDTO.setId(blog.getId());
        blogDTO.setTitle(blog.getTitle());
        blogDTO.setDescription(blog.getDescription());
        blogDTO.setCreatedAt(blog.getCreatedAt());
        blogDTO.setUpdatedAt(blog.getUpdatedAt());
        blogDTO.setCategories(mapCategoriesToDTO(blog.getCategories()));
        return blogDTO;
    }

    public BlogWithCommentDTO mapToDTOWithComment(Blog blog) {
        BlogWithCommentDTO blogDTO = new BlogWithCommentDTO();
        blogDTO.setId(blog.getId());
        blogDTO.setTitle(blog.getTitle());
        blogDTO.setDescription(blog.getDescription());
        blogDTO.setCreatedAt(blog.getCreatedAt());
        blogDTO.setUpdatedAt(blog.getUpdatedAt());
        blogDTO.setComments(mapCommentsToDTO(blog.getComments()));
        return blogDTO;
    }

    public List<CategoryDTO> mapCategoriesToDTO(List<Category> categories) {
        return categories.stream()
                .map(category -> {
                    CategoryDTO categoryDTO = new CategoryDTO();
                    categoryDTO.setId(category.getId());
                    categoryDTO.setName(category.getName());
                    return categoryDTO;
                })
                .collect(Collectors.toList());
    }

    public List<CommentDTO> mapCommentsToDTO(List<Comment> comments) {
        return comments.stream()
                .map(comment -> {
                    CommentDTO commentDTO = new CommentDTO();
                    commentDTO.setId(comment.getId());
                    commentDTO.setMessage(comment.getMessage());
                    return commentDTO;
                })
                .collect(Collectors.toList());
    }
}
