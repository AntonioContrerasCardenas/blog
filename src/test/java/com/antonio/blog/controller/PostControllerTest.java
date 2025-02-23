package com.antonio.blog.controller;

import com.antonio.blog.dto.PostDto;
import com.antonio.blog.service.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//Test de integracion
@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PostService postService;

    @Test
    void getPostById_ValidId_ReturnsPostDto() throws Exception {
        // Arrange
        PostDto postDto = new PostDto();
        postDto.setPostId(1L);
        postDto.setTitle("Test Post");

        when(postService.getPostById(1L)).thenReturn(postDto);

        // Act & Assert
        mockMvc.perform(get("/api/v1/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Post"));
    }
}
