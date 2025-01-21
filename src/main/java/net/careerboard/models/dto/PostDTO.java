package net.careerboard.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PostDTO {
    private Long userId;
    private String title;
    private String content;
    private String status;
    private Long postId;
}
