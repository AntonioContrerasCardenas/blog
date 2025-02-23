package com.antonio.blog.service;

import com.antonio.blog.dto.PostDto;
import com.antonio.blog.entity.Category;
import com.antonio.blog.entity.Post;
import com.antonio.blog.entity.User;
import com.antonio.blog.exception.AccessDeniedException;
import com.antonio.blog.repository.CategoryRepo;
import com.antonio.blog.repository.PostRepo;
import com.antonio.blog.repository.UserRepo;
import com.antonio.blog.service.impl.PostServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//Test unitario
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepo postRepo;

    @Mock
    private CategoryRepo categoryRepo;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private PostServiceImp postService;

    @Test
    void createPost_ValidInput_ReturnsPostDto() {
        // Arrange
        PostDto postDto = new PostDto();
        postDto.setTitle("Test Title");
        postDto.setContent("Test Content");

        User user = new User();
        user.setId(1L);

        Category category = new Category();
        category.setCategoryId(1L);

        Post post = new Post();
        post.setTitle("Test Title");
        post.setContent("Test Content");
        post.setUser(user);
        post.setCategory(category);

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(categoryRepo.findById(1L)).thenReturn(Optional.of(category));
        when(modelMapper.map(postDto, Post.class)).thenReturn(post);
        when(postRepo.save(any(Post.class))).thenReturn(post);
        when(modelMapper.map(post, PostDto.class)).thenReturn(postDto);

        // Act
        PostDto result = postService.createPost(postDto, 1L, 1L);

        // Assert
        assertNotNull(result);
        assertEquals("Test Title", result.getTitle());
        verify(userRepo, times(1)).findById(1L);
        verify(categoryRepo, times(1)).findById(1L);
        verify(postRepo, times(1)).save(any(Post.class));
    }

    @Test
    void updatePost_NotOwner_ThrowsAccessDeniedException() {
        // Arrange
        PostDto postDto = new PostDto();
        postDto.setTitle("Updated Title");

        User user = new User();
        user.setId(2L); // Usuario diferente al dueño del post

        Post post = new Post();
        post.setPostId(1L);
        post.setUser(user);

        when(postRepo.findById(1L)).thenReturn(Optional.of(post));

        // Act & Assert
        assertThrows(AccessDeniedException.class, () -> postService.updatePost(postDto, 1L, 1L));
        verify(postRepo, times(1)).findById(1L);
        verify(postRepo, never()).save(any(Post.class));
    }

    @Test
    void deletePost_ValidInput_DeletesPost() {
        // Arrange
        User user = new User();
        user.setId(1L);

        Post post = new Post();
        post.setPostId(1L);
        post.setUser(user);

        when(postRepo.findById(1L)).thenReturn(Optional.of(post));

        // Act
        postService.deletePost(1L, 1L);

        // Assert
        verify(postRepo, times(1)).findById(1L);
        verify(postRepo, times(1)).delete(post);
    }

    @Test
    void deletePost_NotOwner_ThrowsAccessDeniedException() {
        // Arrange
        User user = new User();
        user.setId(2L); // Usuario diferente al dueño del post

        Post post = new Post();
        post.setPostId(1L);
        post.setUser(user);

        when(postRepo.findById(1L)).thenReturn(Optional.of(post));

        // Act & Assert
        assertThrows(AccessDeniedException.class, () -> postService.deletePost(1L, 1L));
        verify(postRepo, times(1)).findById(1L);
        verify(postRepo, never()).delete(any(Post.class));
    }
}
