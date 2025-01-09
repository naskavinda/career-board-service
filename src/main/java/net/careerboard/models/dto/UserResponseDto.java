package net.careerboard.models.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import net.careerboard.models.Role;


@Data
@AllArgsConstructor
public class UserResponseDto {

    private String firstName;
    private String lastName;
    private Role roles;
}

