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

import com.reach.blog.dto.CommentDTO;
import com.reach.blog.services.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentDTO>> getComments() {
        return ResponseEntity.ok(commentService.findAll());
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.findById(id));
    }

    @GetMapping("/blogs/{blogId}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsByBlogId(@PathVariable(name = "blogId") Long blogId) {
        return ResponseEntity.ok(commentService.findAllByBlogId(blogId));
    }

    @GetMapping("/blogs/{blogId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentByBlogId(@PathVariable(name = "blogId") Long blogId,
            @PathVariable(name = "commentId") Long commentId) {
        return ResponseEntity.ok(commentService.findOneByBlogId(blogId, commentId));
    }

    @PostMapping("blogs/{blogId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable(name = "blogId") Long blogId,
            @Valid @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(commentService.create(blogId, commentDTO));
    }

    @PutMapping("blogs/{blogId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable(name = "blogId") Long blogId,
            @PathVariable(name = "commentId") Long commentId, @Valid @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(commentService.update(blogId, commentId, commentDTO));
    }

    @DeleteMapping("blogs/{blogId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(Long blogId, Long commentId) {
        commentService.delete(blogId, commentId);
        return new ResponseEntity<>("Comment deleted", HttpStatus.OK);
    }

}
