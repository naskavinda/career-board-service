package net.careerboard.models.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PostResponse {

    private Long userId;
    private String username;
    private String title;
    private String status;
    private String postId;
    private LocalDateTime createdAt;
}
