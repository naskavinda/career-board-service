package net.careerboard.controllers;

import lombok.RequiredArgsConstructor;
import net.careerboard.models.Post;
import net.careerboard.models.PostLifecycle;
import net.careerboard.models.User;
import net.careerboard.models.dto.PostRequest;
import net.careerboard.services.PostService;
import net.careerboard.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.findAllPosts();
        return ResponseEntity.ok(posts);
    }

    // Method to fetch all posts by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable Long userId) {
        List<Post> posts = postService.findPostsByUserId(userId);
        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(posts);
        }
    }

    // Method to fetch a post by its ID
    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId) {
        Optional<Post> postOptional = postService.findById(postId);
        if (postOptional.isPresent()) {
            return ResponseEntity.ok(postOptional.get());
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Post with ID " + postId + " not found");
        }
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostRequest request) {
        Optional<User> userOptional = userService.findById(request.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Post post = new Post();
            post.setUser(user);
            post.setTitle(request.getTitle());
            post.setContent(request.getContent());
            post.setCreatedAt(LocalDateTime.now());
            post.setStatus(PostLifecycle.valueOf(request.getStatus()));

            Post savedPost = postService.addPost(post);
            return ResponseEntity.ok(savedPost.getPostDTO());
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("User with ID " + request.getUserId() + " not found");
        }
    }

}
