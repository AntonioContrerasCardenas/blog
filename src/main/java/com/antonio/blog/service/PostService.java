package com.antonio.blog.service;

import com.antonio.blog.dto.PostDto;
import com.antonio.blog.entity.Post;
import com.antonio.blog.utils.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto, Long userId, Long categoryId);

    PostDto updatePost(PostDto postDto, Long id);

    PostDto getPostById(Long id);

    void deletePost(Long id);

    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sort, String sortOrder);

    List<PostDto> getPostsByCategory(Long categoryId);

    List<PostDto> getPostsByUser(Long userId);

    List<PostDto> searchPosts(String searchTerm);

    boolean isPostOwner(Long postId, Long userId);

}
