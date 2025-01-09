package net.careerboard.services;

import net.careerboard.models.User;
import net.careerboard.repos.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addUser() throws Exception {
        User user = new User();
        when(userRepo.save(any(User.class))).thenReturn(user);

        User createdUser = userService.addUser(user);

        assertEquals(user, createdUser);
        verify(userRepo, times(1)).save(user);
    }

    @Test
    void findById() {
        Long userId = 1L;
        User user = new User();
        when(userRepo.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findById(userId);

        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
        verify(userRepo, times(1)).findById(userId);
    }
}