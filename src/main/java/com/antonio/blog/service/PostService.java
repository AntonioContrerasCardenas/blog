package com.antonio.blog.service;

import com.antonio.blog.dto.PostDto;
import com.antonio.blog.entity.Post;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto, Long userId, Long categoryId);

    Post updatePost(PostDto postDto, Long id);

    Post getPostById(Long id);

    void deletePost(Long id);

    List<Post> getAllPosts();

    List<Post> getPostsByCategory(Long categoryId);

    List<Post> getPostsByUser(Long userId);

    List<Post> searchPosts(String searchTerm);

}
