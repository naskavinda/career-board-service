package net.careerboard.services;

import net.careerboard.models.Post;
import net.careerboard.models.User;
import net.careerboard.models.dto.PostRequest;
import net.careerboard.models.dto.PostResponse;
import net.careerboard.repos.PostRepository;
import net.careerboard.repos.UserRepo;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PostServiceTest {
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Mock
    private UserService userService;

    @Mock
    private UserRepo userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPost() throws BadRequestException {
        // Arrange
        User user = new User();
        user.setUserId(1L);
        user.setUsername("j.doe");
        user.setFirstName("John");
        user.setLastName("Doe");

        Post post = new Post();
        post.setUser(user);
        PostRequest postRequest = new PostRequest();
        postRequest.setUserId(1L);
        postRequest.setImageNames(Arrays.asList("image1.jpg", "image2.jpg"));
        postRequest.setTitle("Post title");
        postRequest.setContent("Post content");
        postRequest.setStatus("PUBLISHED");
        when(postRepository.save(any(Post.class))).thenReturn(post);

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(userService.findById(any(Long.class))).thenReturn(Optional.of(user));


        // Act
        Post createdPost = postService.createPost(postRequest);

        // Assert
        assertEquals(post, createdPost);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void findAllPosts() {
        List<Post> posts = Arrays.asList(new Post(), new Post());
        when(postRepository.findAll()).thenReturn(posts);

        List<Post> foundPosts = postService.findAllPosts();

        assertEquals(posts, foundPosts);
        verify(postRepository, times(1)).findAll();
    }

    @Test
    void findPostsByUserId() {
        Long userId = 1L;
        List<Post> posts = Arrays.asList(new Post(), new Post());
        when(postRepository.findByUserUserId(userId)).thenReturn(posts);

        List<Post> foundPosts = postService.findPostsByUserId(userId);

        assertEquals(posts, foundPosts);
        verify(postRepository, times(1)).findByUserUserId(userId);
    }

//    @Test
//    void findById() throws BadRequestException {
//        Long postId = 1L;
//        Post post = new Post();
//        post.setPostId(postId);
//        post.setTitle("Post title");
//        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
//
//        PostResponse foundPost = postService.findById(postId);
//
//        assertEquals(post.getPostId(), foundPost.getPostId());
//        verify(postRepository, times(1)).findById(postId);
//    }
}