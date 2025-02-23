package com.antonio.blog.repository;

import com.antonio.blog.entity.Category;
import com.antonio.blog.entity.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepoTest {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findByCategory_ValidCategory_ReturnsPosts() {
        // Arrange
        Category category = new Category();
        category.setCategoryTitle("Technology");
        category.setCategoryDescription("Tech related posts");
        entityManager.persist(category);

        Post post = new Post();
        post.setCategory(category);
        post.setAddedDate(new Date());
        post.setContent("This is a test post");
        post.setContent("This is a test post");
        post.setTitle("Test Post");
        post.setImageName("test.jpg");

        entityManager.persist(post);

        // Act
        List<Post> posts = postRepo.findByCategory(category);

        // Assert
        assertEquals(1, posts.size());
    }
}
