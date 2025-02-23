package com.antonio.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class JwtAuthRequest {
    @Schema(description = "Email del usuario", example = "john@example.com")
    private String username;

    @Schema(description = "Contraseña del usuario", example = "Passw0rd!")
    private String password;
}
