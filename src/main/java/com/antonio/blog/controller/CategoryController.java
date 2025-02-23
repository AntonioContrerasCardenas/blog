package com.antonio.blog.controller;

import com.antonio.blog.dto.CategoryDto;
import com.antonio.blog.service.CategoryService;
import com.antonio.blog.service.UserService;
import com.antonio.blog.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Categorías", description = "APIs para gestionar categorías")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @Operation(
            summary = "Crear una categoría",
            description = "Crea una nueva categoría. Solo usuarios con rol ADMIN pueden realizar esta acción.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Categoría creada exitosamente",
            content = @Content(schema = @Schema(implementation = CategoryDto.class)))
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la nueva categoría",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CategoryDto.class)))
            @Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto category = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Actualizar una categoría",
            description = "Actualiza una categoría existente. Solo usuarios con rol ADMIN pueden realizar esta acción.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Categoría actualizada exitosamente",
            content = @Content(schema = @Schema(implementation = CategoryDto.class)))
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados de la categoría",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CategoryDto.class)))
            @Valid @RequestBody CategoryDto categoryDto, @PathVariable("id") Long id) {
        CategoryDto category = this.categoryService.updateCategory(categoryDto, id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener una categoría por ID",
            description = "Retorna una categoría específica basada en su ID."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Categoría encontrada",
            content = @Content(schema = @Schema(implementation = CategoryDto.class)))
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") Long id) {
        CategoryDto category = this.categoryService.getCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @Operation(
            summary = "Eliminar una categoría",
            description = "Elimina una categoría existente. Solo usuarios con rol ADMIN pueden realizar esta acción.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Categoría eliminada exitosamente",
            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("id") Long id) {
        this.categoryService.deleteCategory(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully", true), HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener todas las categorías",
            description = "Retorna una lista de todas las categorías disponibles."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Lista de categorías encontrada",
            content = @Content(schema = @Schema(implementation = CategoryDto.class, type = "array")))
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = this.categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
