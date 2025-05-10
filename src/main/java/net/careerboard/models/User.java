package net.careerboard.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user_account")
@Getter
@Setter
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long userId;
    @Column(unique = true, nullable = false, length = 30, name = "username")
    @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
    @NotNull(message = "Username cannot be null")
    String username;
    @Column(name = "first_name", nullable = false)
    @Size(max = 30, message = "First name must not exceed 30 characters")
    @NotNull(message = "First name cannot be null")
    String firstName;

    @Column(name = "password", nullable = false)
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
    @NotNull(message = "Password cannot be null")
    String password;

    @Column(name = "last_name", nullable = false)
    @Size(max = 30, message = "Last name must not exceed 30 characters")
    @NotNull(message = "Last name cannot be null")
    String lastName;
    @Column(nullable = false, updatable = false, name = "created_at")
    LocalDateTime createdAt;
    @Column(nullable = false, name = "active")
    Boolean active;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Post> posts;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "role")
    @NotNull(message = "Role cannot be null")
    private Role role;

    public User() {
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }

    public User(String username, String password, String firstName, String lastName) {
        super();
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
