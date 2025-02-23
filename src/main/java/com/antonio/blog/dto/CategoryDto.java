package com.antonio.blog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
//    @JsonIgnore
    @Schema(description = "ID único de la categoría", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long categoryId;

    @NotBlank
    @Size(min = 3, max = 100, message = "Category title must be min of 3 and max of 100 characters")
    @Schema(description = "Título de la categoría", example = "Tecnología", requiredMode = Schema.RequiredMode.REQUIRED)
    private String categoryTitle;

    @NotBlank
    @Size(min = 3, max = 500, message = "Category description must be min of 3 and max of 500 characters")
    @Schema(description = "Descripción de la categoría", example = "Artículos relacionados con tecnología", requiredMode = Schema.RequiredMode.REQUIRED)
    private String categoryDescription;
}
