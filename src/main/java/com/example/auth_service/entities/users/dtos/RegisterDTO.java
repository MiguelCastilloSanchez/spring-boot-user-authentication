package com.example.auth_service.entities.users.dtos;

import com.example.auth_service.entities.users.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
    @NotBlank
    @Size(min = 3, max = 30)
    String name,

    @NotBlank
    @Email
    String email,

    @NotBlank
    @Size(min = 6, max = 30)
    String password,

    UserRole role
) {
}
