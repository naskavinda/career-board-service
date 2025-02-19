package net.careerboard.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateUserRequest {

    private String username;
    private String password;
    private String currentCompany;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean active;
}
