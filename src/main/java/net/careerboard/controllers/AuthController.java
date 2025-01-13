package net.careerboard.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.careerboard.models.User;
import net.careerboard.models.dto.LoginRequest;
import net.careerboard.models.dto.ResDto;
import net.careerboard.security.jwt.JwtUtil;
import net.careerboard.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<ResDto<Object>> registerUser(@RequestBody User user) {
        ResDto<Object> response = authService.registerUser(user);
        if (response.getSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResDto<Object>> loginUser(@Valid @RequestBody LoginRequest loginRequest) {

        ResDto<Object> authResponse = authService.login(loginRequest);
        if (authResponse.getSuccess()) {

            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }
    }


}

