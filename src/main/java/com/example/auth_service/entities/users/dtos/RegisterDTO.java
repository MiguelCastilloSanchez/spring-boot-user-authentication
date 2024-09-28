package com.example.auth_service.entities.users.dtos;

import com.example.auth_service.entities.users.UserRole;

public record RegisterDTO(String name, String email, String password, UserRole role) {
}
