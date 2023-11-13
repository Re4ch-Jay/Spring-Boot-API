package com.reach.blog.response;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private String message;
    private T data;
    private Integer statusCode;
}
