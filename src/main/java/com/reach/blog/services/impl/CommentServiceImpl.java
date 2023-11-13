package com.reach.blog.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reach.blog.dto.CommentDTO;
import com.reach.blog.exception.NotFoundException;
import com.reach.blog.mapper.CommentMapper;
import com.reach.blog.models.Blog;
import com.reach.blog.models.Comment;
import com.reach.blog.repository.BlogRepository;
import com.reach.blog.repository.CommentRepository;
import com.reach.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private BlogRepository blogRepository;
    private CommentMapper mapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, BlogRepository blogRepository,
            CommentMapper mapper) {
        this.commentRepository = commentRepository;
        this.blogRepository = blogRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CommentDTO> findAll() {
        List<Comment> comments = commentRepository.findAll();

        List<CommentDTO> commentList = comments.stream().map(c -> mapper.mapToDTO(c)).toList();

        return commentList;
    }

    @Override
    public CommentDTO findById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment could not be found"));
        return mapper.mapToDTO(comment);
    }

    @Override
    public CommentDTO create(Long blogId, CommentDTO commentDTO) {
        Comment comment = new Comment();

        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new NotFoundException("Blog could not be found"));

        comment.setMessage(commentDTO.getMessage());
        comment.setCreatedAt(new Date());
        comment.setUpdatedAt(new Date());
        comment.setBlog(blog);

        commentRepository.save(comment);

        CommentDTO newComment = new CommentDTO();
        newComment.setId(comment.getId());
        newComment.setMessage(comment.getMessage());
        newComment.setCreatedAt(comment.getCreatedAt());
        newComment.setUpdatedAt(comment.getUpdatedAt());

        return newComment;
    }

    @Override
    public CommentDTO update(Long blogId, Long commentId, CommentDTO commentDTO) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new NotFoundException("Blog could not be found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment could not be found"));

        if (comment.getBlog().getId() != blog.getId()) {
            throw new NotFoundException("Comment is not belong to blog");
        }

        comment.setMessage(commentDTO.getMessage());
        comment.setUpdatedAt(new Date());

        commentRepository.save(comment);

        return mapper.mapToDTO(comment);
    }

    @Override
    public void delete(Long blogId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment could not be found"));
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new NotFoundException("Blog could not be found"));
        if (comment.getBlog().getId() != blog.getId()) {
            throw new NotFoundException("Comment is not belong to blog");
        }
        commentRepository.delete(comment);
    }

    @Override
    public List<CommentDTO> findAllByBlogId(Long blogId) {
        List<Comment> comments = commentRepository.findByBlogId(blogId);
        return comments.stream().map(c -> mapper.mapToDTO(c)).toList();
    }

    @Override
    public CommentDTO findOneByBlogId(Long blogId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment could not be found"));
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new NotFoundException("Blog could not be found"));
        if (comment.getBlog().getId() != blog.getId()) {
            throw new NotFoundException("Comment is not belong to blog");
        }

        return mapper.mapToDTO(comment);
    }

}
