package com.antonio.blog.repository;

import com.antonio.blog.entity.Category;
import com.antonio.blog.entity.Post;
import com.antonio.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);
}
