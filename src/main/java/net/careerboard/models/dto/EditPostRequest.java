package net.careerboard.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditPostRequest extends PostRequest {
    private Long postId;
    private String moderatorComment;
}
