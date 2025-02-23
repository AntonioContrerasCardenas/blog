package com.antonio.blog.controller;

import com.antonio.blog.dto.CommentDto;
import com.antonio.blog.service.CategoryService;
import com.antonio.blog.service.CommentService;
import com.antonio.blog.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Comentarios", description = "APIs para gestionar comentarios en posts")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Operation(
            summary = "Crear un comentario",
            description = "Crea un nuevo comentario en un post específico"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Comentario creado exitosamente",
            content = @Content(schema = @Schema(implementation = CommentDto.class)))
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable("postId") Long postId) {
        CommentDto comment = this.commentService.createComment(commentDto, postId);
        return new ResponseEntity<CommentDto>(comment, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Eliminar un comentario",
            description = "Elimina un comentario específico por su ID"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Comentario eliminado exitosamente",
            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Long commentId) {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment deleted successfully", true), HttpStatus.OK);
        }
}
