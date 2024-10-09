package com.example.auth_service.entities.users.dtos;

import com.example.auth_service.entities.users.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
    @NotBlank
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    String name,

    @NotBlank
    @Email(message = "Email is not valid")
    String email,

    @NotBlank
    @Size(min = 6, max = 30, message = "Password must be between 6 and 30 characters")
    String password,

    UserRole role
) {
}
