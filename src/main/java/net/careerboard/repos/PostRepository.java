package net.careerboard.repos;

import net.careerboard.models.Post;
import net.careerboard.models.PostLifecycle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserUserIdOrderByCreatedAtDesc(Long userId);
    List<Post> findByUserUserIdAndStatus(Long userId, PostLifecycle status);
}
