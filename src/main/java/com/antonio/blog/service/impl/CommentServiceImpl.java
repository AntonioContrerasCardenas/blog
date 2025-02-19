package com.antonio.blog.service.impl;

import com.antonio.blog.dto.CommentDto;
import com.antonio.blog.entity.Comment;
import com.antonio.blog.entity.Post;
import com.antonio.blog.exception.ResourceNotFoundException;
import com.antonio.blog.repository.CommentRepo;
import com.antonio.blog.repository.PostRepo;
import com.antonio.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = this.commentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment Id", id));
        this.commentRepo.deleteById(id);
    }
}
