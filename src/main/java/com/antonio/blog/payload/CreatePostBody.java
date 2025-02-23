package com.antonio.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Body para la creación de posts")
public class CreatePostBody {
    @Schema(description = "Título del post", example = "Introducción a Spring Boot", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(description = "Contenido del post", example = "Spring Boot es un framework...", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;
}
