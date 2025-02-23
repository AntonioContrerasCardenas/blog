package com.antonio.blog.repository;

import com.antonio.blog.entity.Category;
import com.antonio.blog.entity.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class PostRepoTest {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findByCategory_ValidCategory_ReturnsPosts() {
        // Arrange
        Category category = new Category();
        entityManager.persist(category);

        Post post = new Post();
        post.setCategory(category);
        entityManager.persist(post);

        // Act
        List<Post> posts = postRepo.findByCategory(category);

        // Assert
        assertEquals(1, posts.size());
    }
}
