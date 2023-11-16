package com.reach.blog.dto;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    @NotNull
    @NotEmpty
    private String message;
    private Date createdAt;
    private Date updatedAt;
}
