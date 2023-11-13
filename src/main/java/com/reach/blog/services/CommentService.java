package com.reach.blog.services;

import java.util.List;

import com.reach.blog.dto.CommentDTO;

public interface CommentService {
    List<CommentDTO> findAll();

    List<CommentDTO> findAllByBlogId(Long blogId);

    CommentDTO findOneByBlogId(Long blogId, Long commentId);

    CommentDTO findById(Long commentId);

    CommentDTO create(Long blogId, CommentDTO commentDTO);

    CommentDTO update(Long blogId, Long commentId, CommentDTO commentDTO);

    void delete(Long blogId, Long commentId);
}
