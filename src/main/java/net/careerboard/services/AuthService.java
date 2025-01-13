package net.careerboard.services;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import net.careerboard.models.Role;
import net.careerboard.models.User;
import net.careerboard.models.dto.*;
import net.careerboard.repos.UserRepo;
import net.careerboard.security.jwt.JwtUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepo userRepo;

    @Transactional
    public ResDto<Object> registerUser(User user) {
        try {
            if (userService.existsByUsername(user.getUsername())) {
                return new ResDto<>(Boolean.FALSE, ResDTOMessage.USERNAME_ALREADY_EXIST, user.getUsername());
            }

            validateUser(user);

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Role.USER);
            userService.addUser(user);

            UserResponseDto userResponse = new UserResponseDto(
                    user.getFirstName(),
                    user.getLastName(),
                    user.getRole()
            );

            return new ResDto<>(Boolean.TRUE, ResDTOMessage.CREATED, userResponse);
        } catch (ConstraintViolationException e) {
            String errors = e.getConstraintViolations()
                    .stream()
                    .map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
                    .collect(Collectors.joining(", "));
            return new ResDto<>(Boolean.FALSE, ResDTOMessage.VALIDATION_ERROR, errors);
        } catch (DataIntegrityViolationException e) {
            return new ResDto<>(Boolean.FALSE, ResDTOMessage.DATABASE_CONSTRAINT_VIOLATION, e.getRootCause().getMessage());
        } catch (Exception e) {
            return new ResDto<>(Boolean.FALSE, ResDTOMessage.UNEXPECTED_ERROR, e.getMessage());
        }
    }

    public ResDto<Object> login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
            );
            Optional<User> optionalUser = userRepo.findByUsername(loginRequest.username());
            if (optionalUser.isEmpty()) {
                return new ResDto<>(Boolean.FALSE, ResDTOMessage.NOT_FOUND, loginRequest.username());

            }
            User user = optionalUser.get();
            String token = jwtUtil.generateToken(user);

            System.err.println(token);

            AuthResponse authResponse = new AuthResponse(token);
            return new ResDto<>(Boolean.TRUE, ResDTOMessage.SUCCESS, authResponse);
        } catch (BadCredentialsException e) {
            return new ResDto<>(Boolean.FALSE, ResDTOMessage.WRONG_USERNAME_OR_PASSWORD, null);
        } catch (Exception e) {
            return new ResDto<>(Boolean.FALSE, ResDTOMessage.NOT_FOUND, null);
        }
    }

    private void validateUser(User user) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
