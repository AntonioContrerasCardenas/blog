package com.antonio.blog.dto;

import com.antonio.blog.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoleDto {
    @JsonIgnore
    private Long id;

    @Schema(description = "Nombre del rol", example = "NORMAL", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
}
