package com.antonio.blog.controller;

import com.antonio.blog.config.AppConstants;
import com.antonio.blog.dto.PostDto;
import com.antonio.blog.entity.Post;
import com.antonio.blog.entity.User;
import com.antonio.blog.service.CategoryService;
import com.antonio.blog.service.FileService;
import com.antonio.blog.service.PostService;
import com.antonio.blog.utils.ApiResponse;
import com.antonio.blog.utils.PostResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(name = "Posts", description = "APIs para gestionar posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

//    @PostMapping("/user/{userId}/category/{categoryId}/posts")
//    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
//                                              @PathVariable("userId") Long userId,
//                                              @PathVariable("categoryId") Long categoryId) {
//        PostDto post = this.postService.createPost(postDto, userId, categoryId);
//        return new ResponseEntity<PostDto>(post, HttpStatus.CREATED);
//    }

    @Operation(
            summary = "Crear un nuevo post",
            description = "Crea un post asociado a una categoría y al usuario autenticado",
            security = @SecurityRequirement(name = "bearerAuth"))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Post creado exitosamente",
            content = @Content(schema = @Schema(implementation = PostDto.class)))
    @PostMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del nuevo post",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PostDto.class)))
            @RequestBody PostDto postDto,
            @PathVariable("categoryId") Long categoryId,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        PostDto post = this.postService.createPost(postDto, user.getId(), categoryId);
        return new ResponseEntity<PostDto>(post, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Obtener posts por usuario",
            description = "Retorna una lista de posts creados por un usuario específico"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Posts encontrados",
            content = @Content(schema = @Schema(implementation = PostDto.class, type = "array")))
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable("userId") Long userId) {
        List<PostDto> posts = this.postService.getPostsByUser(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener posts por categoría",
            description = "Retorna una lista de posts asociados a una categoría específica"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Posts encontrados",
            content = @Content(schema = @Schema(implementation = PostDto.class, type = "array")))
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("categoryId") Long categoryId) {
        List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener todos los posts",
            description = "Retorna una lista paginada de posts con opciones de ordenamiento"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Posts encontrados",
            content = @Content(schema = @Schema(implementation = PostResponse.class)))
    @GetMapping("")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGENUMBER_DEFAULT, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGESIZE_DEFAULT, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORTBY_DEFAULT, required = false) String sort,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORTORDER_DEFAULT, required = false) String sortOrder
    ) {
        PostResponse postsResponse = this.postService.getAllPosts(pageNumber, pageSize, sort, sortOrder);
        return new ResponseEntity<>(postsResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener un post por ID",
            description = "Retorna un post específico basado en su ID"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Post encontrado",
            content = @Content(schema = @Schema(implementation = PostDto.class)))
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Long postId) {
        PostDto post = this.postService.getPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @Operation(
            summary = "Actualizar un post",
            description = "Actualiza un post existente. Solo el dueño del post puede actualizarlo.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Post actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = PostDto.class)))
    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("postId") Long postId, @RequestBody PostDto postDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        PostDto post = this.postService.updatePost(postDto, postId, user.getId());
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @Operation(
            summary = "Eliminar un post",
            description = "Elimina un post existente. Solo el dueño del post puede eliminarlo.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Post eliminado exitosamente",
            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Long postId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        this.postService.deletePost(postId, user.getId());
        return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted successfully", true), HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar posts por término",
            description = "Retorna una lista de posts que coinciden con el término de búsqueda"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Posts encontrados",
            content = @Content(schema = @Schema(implementation = PostDto.class, type = "array")))
    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<List<PostDto>> searchPosts(@PathVariable("searchTerm") String searchTerm) {
        List<PostDto> posts = this.postService.searchPosts(searchTerm);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @Operation(
            summary = "Subir imagen para un post",
            description = "Sube una imagen y la asocia a un post existente. Solo el dueño del post puede subir imágenes.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Imagen subida exitosamente",
            content = @Content(schema = @Schema(implementation = PostDto.class)))
    @PostMapping("/images/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @PathVariable("postId") Long postId,
            @RequestParam("file") MultipartFile file,
            Authentication authentication
    ) throws IOException {
        User user = (User) authentication.getPrincipal();
        String fileName = this.fileService.uploadImage(path, file);
        PostDto post = this.postService.getPostById(postId);
        post.setImageName(fileName);
        PostDto savedPost = this.postService.updatePost(post, postId , user.getId());
        return new ResponseEntity<PostDto>(savedPost, HttpStatus.OK);

    }

    @Operation(
            summary = "Obtener imagen de un post",
            description = "Retorna la imagen asociada a un post"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Imagen encontrada",
            content = @Content(mediaType = "image/jpeg"))
    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
        InputStream is = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(is, response.getOutputStream());
        return ResponseEntity.ok().build();
    }
}
