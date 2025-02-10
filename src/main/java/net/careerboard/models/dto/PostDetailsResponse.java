package net.careerboard.models.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class PostDetailsResponse {
    private Long userId;
    private String username;
    private String title;
    private String content;
    private String status;
    private Long postId;
    private LocalDateTime createdAt;
    private String moderatorComment;
    private List<PostImageDto> images;
}
