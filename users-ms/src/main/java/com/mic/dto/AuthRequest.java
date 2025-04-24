package com.mic.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class AuthRequest {

    @NotNull(message = "The email is required")
    @NotBlank(message = "The email is required")
    @Email(message = "Email must be in a valid format")
    private String email;

    @NotNull(message = "The password is required")
    @NotBlank(message = "The password is required")
    @Length(min = 5, message = "The password should be at least of 5 characters of length")
    private String password;

}
