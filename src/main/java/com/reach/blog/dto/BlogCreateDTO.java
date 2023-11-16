package com.reach.blog.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogCreateDTO extends BlogDTO {
    @NotNull
    @NotEmpty
    private List<Long> categoryIds;
}
