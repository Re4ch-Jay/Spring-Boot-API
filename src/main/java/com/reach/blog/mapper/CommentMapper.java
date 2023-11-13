package com.reach.blog.mapper;

import org.springframework.stereotype.Component;

import com.reach.blog.dto.CommentDTO;
import com.reach.blog.models.Comment;

@Component
public class CommentMapper extends Mapper {
    public CommentDTO mapToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setMessage(comment.getMessage());
        commentDTO.setCreatedAt(comment.getCreatedAt());
        commentDTO.setUpdatedAt(comment.getUpdatedAt());

        return commentDTO;
    }
}
