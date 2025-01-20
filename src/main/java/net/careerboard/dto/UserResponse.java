package net.careerboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private Boolean active;
    private String role;
    private int postCount;
}
