package net.careerboard.repos;

import net.careerboard.models.User;
import net.careerboard.models.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("j.doe");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setCreatedAt(LocalDateTime.now());
        entityManager.persist(user);
        entityManager.flush();
    }


    @Test
    void findByUserUserId() {
        Post post1 = new Post();
        post1.setUser(user);
        post1.setTitle("Post 1 title");
        post1.setContent("Post 1 content");
        post1.setCreatedAt(LocalDateTime.now());
        entityManager.persist(post1);

        Post post2 = new Post();
        post2.setUser(user);
        post2.setTitle("Post 2 title");
        post2.setContent("Post 2 content");
        post2.setCreatedAt(LocalDateTime.now());
        entityManager.persist(post2);

        entityManager.flush();

        List<Post> posts = postRepository.findByUserUserId(user.getUserId());

        assertNotNull(posts);
        assertEquals(2, posts.size());
        assertEquals("Post 1 content", posts.get(0).getContent());
        assertEquals("Post 2 content", posts.get(1).getContent());
    }
}