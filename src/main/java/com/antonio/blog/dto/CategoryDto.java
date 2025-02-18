package com.antonio.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long categoryId;

    @NotBlank
    @Size(min = 3, max = 100, message = "Category title must be min of 3 and max of 100 characters")
    private String categoryTitle;

    @NotBlank
    @Size(min = 3, max = 500, message = "Category description must be min of 3 and max of 500 characters")
    private String categoryDescription;
}
