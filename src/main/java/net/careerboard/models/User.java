package net.careerboard.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_account")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long userId;

    @Column(unique = true, nullable = false, length = 30, name = "username")
    @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
    @NotNull(message = "Username cannot be null")
    String username;

    @Column(name = "password", nullable = false)
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
    @NotNull(message = "Password cannot be null")
    String password;

    @Column(name = "current_company")
    @Size(max = 30, message = "Company name must not exceed 30 characters")
    String currentCompany;

    @Column(nullable = false, updatable = false, name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false, name = "active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    Boolean active = true;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Post> posts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "role")
    @NotNull(message = "Role cannot be null")
    private Role role;

}
