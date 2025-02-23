package com.antonio.blog.dto;

import com.antonio.blog.entity.Category;
import com.antonio.blog.entity.Comment;
import com.antonio.blog.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para la creación y gestión de posts")
public class PostDto {

//    @JsonIgnore
@Schema(description = "ID único del post", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long postId;

    @Schema(description = "Título del post", example = "Introducción a Spring Boot", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(description = "Contenido del post", example = "Spring Boot es un framework...", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Schema(description = "Nombre de la imagen asociada al post", example = "default.png")
    private String imageName = "default.png";

    @Schema(description = "Fecha de creación del post", example = "2023-12-25T15:30:45", accessMode = Schema.AccessMode.READ_ONLY)
    private Date addedDate;

    @Schema(description = "Usuario que creó el post", implementation = UserDto.class)
    private UserDto user;

    @Schema(description = "Categoría a la que pertenece el post", implementation = CategoryDto.class)
    private CategoryDto category;

    @Schema(description = "Lista de comentarios asociados al post", implementation = CommentDto.class)
    private List<CommentDto> comments = new ArrayList<>();
}
