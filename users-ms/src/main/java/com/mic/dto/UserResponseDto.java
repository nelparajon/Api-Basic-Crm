package com.mic.dto;

import com.mic.utils.Role;

import java.time.Instant;
import java.time.OffsetDateTime;

public class UserResponseDto {
    private String publicId;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private Instant createdAt;

    // Getters and setters
    public String getPublicId() { return publicId; }
    public void setPublicId(String publicId) { this.publicId = publicId; }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
