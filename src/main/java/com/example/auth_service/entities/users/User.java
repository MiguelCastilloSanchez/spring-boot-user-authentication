package com.example.auth_service.entities.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Document(collection = "users")
public class User implements UserDetails {
    @Id
    private String id;

    private String name;
    private String email;
    private String password;
    private UserRole role;

    public User(String name, String email, String password, UserRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));

        if (this.role == UserRole.ADMIN) roles.add(new SimpleGrantedAuthority("ROLES_ADMIN"));

        return roles;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
      return "User [id=" + id + ", email=" + email + ", name=" + name + ", password=" + password + ", role=" + role.getRole() + "]";
    }
}

