package net.careerboard.services;

import lombok.RequiredArgsConstructor;
import net.careerboard.models.Post;
import net.careerboard.models.PostImage;
import net.careerboard.models.PostLifecycle;
import net.careerboard.models.User;
import net.careerboard.models.dto.PostRequest;
import net.careerboard.repos.PostRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        }catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    public List<Post> findAllPosts() {
        return this.postRepository.findAll();
    }

    public List<Post> findPostsByUserId(Long userId) {
        return this.postRepository.findByUserUserId(userId);
    }

    public List<Post> findPublishedPostsByUserId(Long userId) {
        return this.postRepository.findByUserUserIdAndStatus(userId, PostLifecycle.PUBLISHED);
    }

    public Optional<Post> findById(Long postId) {
        return this.postRepository.findById(postId);
    }
}
