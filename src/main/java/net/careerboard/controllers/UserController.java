package net.careerboard.controllers;

import lombok.RequiredArgsConstructor;
import net.careerboard.models.User;
import net.careerboard.services.UserService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    @Value(value = "${greeting}")
    String greeting;
    private final UserService userService;
    @RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String home() {
        System.out.println("Hello "+greeting);
        return "{'message': 'Hello world'}";
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> authenticatedUser = userService.findByUsername(currentUsername);
        if (authenticatedUser.isEmpty()) {
        return new ResponseEntity<>("Authenticated user not found!", HttpStatus.UNAUTHORIZED);
    }
    if (!authenticatedUser.get().getUserId().equals(userId)) {
        return new ResponseEntity<>("Access denied: You can only access your own details.", HttpStatus.FORBIDDEN);
    }
        var user = userService.findById(userId);
        if (user.isEmpty()) {
            return new ResponseEntity<String>("User with ID=%d not found!".formatted(userId), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user.get(), HttpStatus.OK);
    }

    @PostMapping(value = "/user")
    public ResponseEntity<String> createUser(@RequestBody User user){
        try{
            User createdUser = this.userService.addUser(user);
            return new ResponseEntity<>("User is created successfully: " + createdUser.toString(), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>("User is creation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     @GetMapping("/admin")
        public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

}
