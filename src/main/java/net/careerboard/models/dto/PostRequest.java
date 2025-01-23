package net.careerboard.models.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequest {
    private Long userId;
    private String title;
    private String content;
    private String status;
    private List<String> imageNames;
}
