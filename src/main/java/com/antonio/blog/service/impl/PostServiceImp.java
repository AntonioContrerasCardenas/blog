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
import com.antonio.blog.utils.PostResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public PostDto updatePost(PostDto postDto, Long id) {

        Post post = this.postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post savedPost = this.postRepo.save(post);

        return this.modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = this.postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public void deletePost(Long id) {

        this.postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        this.postRepo.deleteById(id);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sort = (sortOrder.equalsIgnoreCase("ASC")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//        if(sortOrder.equals("ASC")){
//            sort = Sort.by(sortBy).ascending();
//        }else{
//            sort = Sort.by(sortBy).descending();
//        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> postPages = this.postRepo.findAll(pageable);

        List<Post> posts = postPages.getContent();

        PostResponse postResponse = new PostResponse();
        postResponse.setPostNumber(postPages.getNumber());
        postResponse.setPageSize(postPages.getSize());
        postResponse.setTotalPages(postPages.getTotalPages());
        postResponse.setTotalElements(postPages.getTotalElements());
        postResponse.setLastPage(postPages.isLast());

        postResponse.setPosts(posts.stream().map(  (post) -> this.modelMapper.map(post, PostDto.class)).toList());

        return postResponse;

    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        List<Post> posts = this.postRepo.findByCategory(category);

        List<PostDto> postsDto = posts.stream().map(  (post) -> this.modelMapper.map(post, PostDto.class)).toList();

        return postsDto;
    }


    @Override
    public List<PostDto> getPostsByUser(Long userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        List<Post> posts = this.postRepo.findByUser(user);

        List<PostDto> postsDto = posts.stream().map(  (post) -> this.modelMapper.map(post, PostDto.class)).toList();

        return postsDto;
    }

    @Override
    public List<PostDto> searchPosts(String searchTerm) {
        List<Post> posts = this.postRepo.findByTitleContaining(searchTerm);
        //List<Post> posts = this.postRepo.seachByTitle("%" + searchTerm + "%");

        List<PostDto> postsDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).toList();

        return postsDto;
    }
}
