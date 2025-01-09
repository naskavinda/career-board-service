package net.careerboard.models.dto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record LoginRequest(  
    
    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
     String username,

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
     String password) {}
