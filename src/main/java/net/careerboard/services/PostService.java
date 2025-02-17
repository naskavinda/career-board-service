package net.careerboard.services;

import lombok.RequiredArgsConstructor;
import net.careerboard.models.Post;
import net.careerboard.models.PostImage;
import net.careerboard.models.PostLifecycle;
import net.careerboard.models.User;
import net.careerboard.models.dto.*;
import net.careerboard.repos.PostRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    public Post createPost(PostRequest request) throws BadRequestException {
        try {
            Optional<User> userOptional = userService.findById(request.getUserId());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Post post = new Post();
                post.setUser(user);
                post.setTitle(request.getTitle());
                post.setContent(request.getContent());
                post.setCreatedAt(LocalDateTime.now());
                post.setStatus(PostLifecycle.valueOf(request.getStatus()));
                List<PostImage> postImageList = request.getImageNames().stream().map(imageName -> {
                    PostImage postImage = new PostImage();
                    postImage.setImageName(imageName);
                    postImage.setPost(post); // Set the post reference
                    return postImage;
                }).toList();
                post.setImages(postImageList);

                return postRepository.save(post);
            } else {
                throw new BadRequestException("User with ID %d not found!".formatted(request.getUserId()));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    public Page<PostResponse> findAllPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(PostService::mapToPostResponse);
    }

    private static PostResponse mapToPostResponse(Post post) {
        return PostResponse.builder()
                .postId(post.getPostId())
                .userId(post.getUser().getUserId())
                .username(post.getUser().getUsername())
                .title(post.getTitle())
                .status(post.getStatus().name())
                .createdAt(post.getCreatedAt())
                .build();
    }

    public List<Post> findPostsByUserId(Long userId) {
        return this.postRepository.findByUserUserId(userId);
    }

    public List<Post> findPublishedPostsByUserId(Long userId) {
        return this.postRepository.findByUserUserIdAndStatus(userId, PostLifecycle.PUBLISHED);
    }

    public PostDetailsResponse findById(String postId) throws BadRequestException {
        Optional<Post> optionalPost = this.postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            throw new BadRequestException("Post with ID %d not found!".formatted(postId));
        } else {
            Post post = optionalPost.get();
            return PostDetailsResponse.builder()
                    .postId(post.getPostId())
                    .userId(post.getUser().getUserId())
                    .username(post.getUser().getUsername())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .createdAt(post.getCreatedAt())
                    .status(post.getStatus().name())
                    .images(post.getImages().stream().map(postImage -> PostImageDto.builder()
                            .imageId(postImage.getImageId())
                            .imageName(postImage.getImageName())
                            .build()).toList())
                    .moderatorComment(post.getModeratorComment())
                    .build();
        }
    }

    public Post editPost(EditPostRequest request) throws BadRequestException {
        try {
            Optional<User> userOptional = userService.findById(request.getUserId());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Post post = new Post();
                post.setPostId(request.getPostId());
                post.setUser(user);
                post.setTitle(request.getTitle());
                post.setContent(request.getContent());
                post.setCreatedAt(LocalDateTime.now());
                post.setStatus(PostLifecycle.valueOf(request.getStatus()));
                post.setStatus(PostLifecycle.valueOf(request.getStatus()));
                Set<String> rolesWithCommentPermission = Set.of("MODERATOR", "ADMIN");
                SecurityContext context = SecurityContextHolder.getContext();
                List<String> list = context.getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
                System.out.println(list);
                if (rolesWithCommentPermission.contains(list.get(0))) {
                    System.out.println(request.getModeratorComment());
                    post.setModeratorComment(request.getModeratorComment());
                }
                List<PostImage> postImageList = request.getImages().stream().map(image -> {
                    PostImage postImage = new PostImage();
                    postImage.setImageName(image.getImageName());
                    if (image.getImageId() == 0) {
                        postImage.setImageId(postImage.getImageId());
                    }
                    postImage.setPost(post); // Set the post reference
                    return postImage;
                }).toList();
                post.setImages(postImageList);

                return postRepository.save(post);
            } else {
                throw new BadRequestException("User with ID %d not found!".formatted(request.getUserId()));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }
}
