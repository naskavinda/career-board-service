package net.careerboard.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.careerboard.dto.UserResponse;
import net.careerboard.models.User;
import net.careerboard.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    @Value(value = "${greeting}")
    String greeting;

//    @RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody String home() {
//        System.out.println("Hello " + greeting);
//        return "{'message': 'Hello world'}";
//    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> authenticatedUser = userService.findByUsername(currentUsername);
        if (authenticatedUser.isEmpty()) {
            return new ResponseEntity<>("Authenticated user not found!", HttpStatus.UNAUTHORIZED);
        }
        var user = userService.findById(userId);
        if (user.isEmpty()) {
            return new ResponseEntity<String>("User with ID=%d not found!".formatted(userId), HttpStatus.NOT_FOUND);
        }
        if (!authenticatedUser.get().getUserId().equals(userId)) {
            return new ResponseEntity<>("Access denied: You can only access your own details.", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<User>(user.get(), HttpStatus.OK);
    }

    @PostMapping(value = "/user")
    public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
        try {
            User createdUser = this.userService.addUser(user);
            return new ResponseEntity<>("User is created successfully: " + createdUser.toString(), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>("User is creation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            String response = userService.deleteUserById(userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
