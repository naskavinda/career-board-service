package net.careerboard.services;

import lombok.RequiredArgsConstructor;
import net.careerboard.models.User;
import net.careerboard.repos.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.security.Jwks.OP;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

   
    public Optional<User> findById(Long userId) {
        return userRepo.findById(userId);
    }


    public boolean existsByUsername(String username) {
        return userRepo.findByUsername(username).isPresent();
    }

    
    public User addUser(User user) throws Exception {
        if(existsByUsername(user.getUsername())){
            throw new IllegalAccessException("username alredy taken");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);  
    }

    public List<User>getAllUsers(){
        return userRepo.findAll();
    }

    public Optional<User> findByUsername(String username) {
    return userRepo.findByUsername(username);
}
}