package net.careerboard.services;

import lombok.RequiredArgsConstructor;
import net.careerboard.dto.CreateUserRequest;
import net.careerboard.dto.UserResponse;
import net.careerboard.models.User;
import net.careerboard.repos.UserRepo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    public Optional<User> findById(Long userId) {
        return userRepo.findById(userId);
    }

    public boolean existsByUsername(String username) {
        return userRepo.findByUsername(username).isPresent();
    }

    public UserResponse addUser(CreateUserRequest createUserRequest) throws Exception {
        if (existsByUsername(createUserRequest.getUsername())) {
            throw new DataIntegrityViolationException("username already taken");
        }
        User user = mapToUser(createUserRequest);
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        return mapToUserResponse(userRepo.save(user));
    }

    public UserResponse addUser(User user) throws Exception {
        if (existsByUsername(user.getUsername())) {
            throw new DataIntegrityViolationException("username already taken");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return mapToUserResponse(userRepo.save(user));
    }

    private User mapToUser(CreateUserRequest createUserRequest) {
        return User.builder()
                .username(createUserRequest.getUsername())
                .currentCompany(createUserRequest.getCurrentCompany())
                .active(createUserRequest.getActive())
                .build();
    }

    public List<UserResponse> getAllUsers() {
        return userRepo.findAll().stream()
                .map(UserService::mapToUserResponse)
                .toList();
    }

    private static UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .currentCompany(user.getCurrentCompany())
                .createdAt(user.getCreatedAt())
                .active(user.getActive())
                .role(user.getRole().name())
                .postCount(user.getPosts().size())
                .build();
    }

    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public String deleteUserById(Long userId) throws IllegalAccessException {

        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalAccessException("user not available");
        } else if (!user.get().getActive()) {
            throw new IllegalAccessException("user is already inactive");
        } else {
            user.get().setActive(false);
            userRepo.save(user.get());
            return "user is deleted";
        }
    }

}