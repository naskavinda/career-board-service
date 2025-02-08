package net.careerboard.models.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditPostRequest {
    private Long userId;
    private String title;
    private String content;
    private String status;
    private List<PostImageDto> images;
    private Long postId;
    private String moderatorComment;
}
