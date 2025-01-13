package net.careerboard.services;

import lombok.RequiredArgsConstructor;
import net.careerboard.models.Post;
import net.careerboard.repos.PostRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepo postRepo;

    public Post addPost(Post post) {
        return this.postRepo.save(post);
    }

    public List<Post> findAllPosts() {
        return this.postRepo.findAll();
    }

    public List<Post> findPostsByUserId(Long userId) {
        return this.postRepo.findByUserUserId(userId);
    }

    public Optional<Post> findById(Long postId) {
        return this.postRepo.findById(postId);
    }
}
