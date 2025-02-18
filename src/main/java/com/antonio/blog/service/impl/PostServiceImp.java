package com.antonio.blog.service.impl;

import com.antonio.blog.dto.PostDto;
import com.antonio.blog.entity.Category;
import com.antonio.blog.entity.Post;
import com.antonio.blog.entity.User;
import com.antonio.blog.exception.ResourceNotFoundException;
import com.antonio.blog.repository.CategoryRepo;
import com.antonio.blog.repository.PostRepo;
import com.antonio.blog.repository.UserRepo;
import com.antonio.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImp implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto, Long userId, Long categoryId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post savedPost = this.postRepo.save(post);

        return this.modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public Post updatePost(PostDto postDto, Long id) {
        return null;
    }

    @Override
    public Post getPostById(Long id) {
        return null;
    }

    @Override
    public void deletePost(Long id) {

    }

    @Override
    public List<Post> getAllPosts() {
        return List.of();
    }

    @Override
    public List<Post> getPostsByCategory(Long categoryId) {
        return List.of();
    }

    @Override
    public List<Post> getPostsByUser(Long userId) {
        return List.of();
    }

    @Override
    public List<Post> searchPosts(String searchTerm) {
        return List.of();
    }
}
