package com.antonio.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Body para el registro de usuarios")
public class RegisterUserBody {
    @Schema(description = "Nombre del usuario", example = "john_doe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Correo electrónico válido", example = "john@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "Contraseña con al menos 4 caracteres, 1 número y 1 carácter especial",example = "Passw0rd!",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(description = "Información biográfica del usuario", example = "Desarrollador Java apasionado")
    private String about;
}
