package com.reach.blog.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogDTO {
    private Long id;
    private String title;
    private String description;
    private Date createdAt;
    private Date updatedAt;

}
