package com.antonio.blog.controller;

import com.antonio.blog.config.AppConstants;
import com.antonio.blog.dto.PostDto;
import com.antonio.blog.entity.Post;
import com.antonio.blog.service.CategoryService;
import com.antonio.blog.service.FileService;
import com.antonio.blog.service.PostService;
import com.antonio.blog.utils.ApiResponse;
import com.antonio.blog.utils.PostResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable("userId") Long userId,
                                              @PathVariable("categoryId") Long categoryId) {
        PostDto post = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<PostDto>(post, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable("userId") Long userId) {
        List<PostDto> posts = this.postService.getPostsByUser(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("categoryId") Long categoryId) {
        List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

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

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Long postId) {
        PostDto post = this.postService.getPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("postId") Long postId, @RequestBody PostDto postDto) {
        PostDto post = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Long postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<List<PostDto>> searchPosts(@PathVariable("searchTerm") String searchTerm) {
        List<PostDto> posts = this.postService.searchPosts(searchTerm);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping("/images/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @PathVariable("postId") Long postId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        String fileName = this.fileService.uploadImage(path, file);
        PostDto post = this.postService.getPostById(postId);
        post.setImageName(fileName);
        PostDto savedPost = this.postService.updatePost(post, postId);
        return new ResponseEntity<PostDto>(savedPost, HttpStatus.OK);

    }

    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
        InputStream is = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(is, response.getOutputStream());
        return ResponseEntity.ok().build();
    }
}
