package com.antonio.blog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor()
@NoArgsConstructor
public class CommentDto {

//    @JsonIgnore
    @Schema(description = "ID único del comentario", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Contenido del comentario", example = "¡Excelente post!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;
}
