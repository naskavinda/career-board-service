package net.careerboard.controllers;

import lombok.RequiredArgsConstructor;
import net.careerboard.models.Post;
import net.careerboard.models.dto.EditPostRequest;
import net.careerboard.models.dto.PostRequest;
import net.careerboard.models.dto.PostDetailsResponse;
import net.careerboard.models.dto.PostResponse;
import net.careerboard.services.PostService;
import net.careerboard.services.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<PostResponse>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostResponse> posts = postService.findAllPosts(pageable);
        return ResponseEntity.ok(posts);
    }

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
    public ResponseEntity<?> getPostById(@PathVariable String postId) {
        try {

            PostDetailsResponse postOptional = postService.findById(postId);
            return ResponseEntity.ok(postOptional);
        } catch (BadRequestException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostRequest request) {
        try {
            Post savedPost = postService.createPost(request);
            System.out.println("Post created successfully");
            return ResponseEntity.ok(savedPost);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editPost(@RequestBody EditPostRequest request) {
        try {
            Post editPost = postService.editPost(request);
            System.out.println("Post Edit successfully");
            return ResponseEntity.ok(editPost);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

}
