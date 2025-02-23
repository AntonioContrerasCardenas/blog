package com.antonio.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Body para la actualización de posts")
public class UpdatePostBody extends CreatePostBody{
    @Schema(description = "Nombre de la imagen asociada al post", example = "default.png")
    private String imageName = "default.png";
}
