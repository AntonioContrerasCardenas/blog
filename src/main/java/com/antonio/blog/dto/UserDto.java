package com.antonio.blog.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Schema(description = "DTO para registro y gestión de usuarios")
public class UserDto {
    @JsonIgnore
    @Schema(description = "ID único del usuario (automático)", accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Long id;

    @NotEmpty
    @Size(min = 4, message = "Username must be min of 4 characters")
    @Schema(description = "Nombre del usuario", example = "john_doe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Email(message = "Email is not valid")
    @Schema(description = "Correo electrónico válido", example = "john@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @JsonIgnore
    @NotEmpty
    @Size(min = 4, max = 30, message = "Password must be min of 3 and max of 30 characters")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!])[A-Za-z\\d@#$%^&+=!]{4,30}$",
            message = "Password must contain at least one letter, one number, and one special character (@#$%^&+=!)")
    @Schema(description = "Contraseña con al menos 4 caracteres, 1 número y 1 carácter especial",example = "Passw0rd!",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @NotEmpty
    @Schema(description = "Información biográfica del usuario", example = "Desarrollador Java apasionado")
    private String about;

    @Schema(description = "Lista de roles asignados al usuario", accessMode = Schema.AccessMode.READ_ONLY)
    private List<RoleDto> roles = new ArrayList<>();
}
