package com.antonio.blog.service;

import com.antonio.blog.dto.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Long postId);

    void deleteComment(Long id);
}
