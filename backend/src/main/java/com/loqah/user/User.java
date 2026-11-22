package com.loqah.user;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "app_users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true, length = 180)
    private String email;
    @Column(nullable = false)
    private String passwordHash;
    @Column(nullable = false, length = 80)
    private String displayName;
    @Column(nullable = false, length = 30)
    private String role = "USER";
    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    protected User() {}
    public User(String email, String passwordHash, String displayName) {
        this.email = email.toLowerCase().trim();
        this.passwordHash = passwordHash;
        this.displayName = displayName.trim();
    }
    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getDisplayName() { return displayName; }
    public String getRole() { return role; }
    public Instant getCreatedAt() { return createdAt; }
}