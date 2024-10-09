package com.example.auth_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth_service.entities.users.User;
import com.example.auth_service.entities.users.dtos.AuthenticationDTO;
import com.example.auth_service.entities.users.dtos.LoginResponseDTO;
import com.example.auth_service.entities.users.dtos.RegisterDTO;
import com.example.auth_service.repositories.UserRepository;
import com.example.auth_service.services.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/auth", produces = {"application/json"})
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    /**
     * Registers a new user.
     *
     * @param data Object containing user registration data
     * @param result Object checking the validation from registration data
     * @return ResponseEntity indicating success or failure of registration
     */
    @SuppressWarnings("rawtypes")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity register(@Valid @RequestBody RegisterDTO data, BindingResult result) {

        if (result.hasErrors()) {
            String error = result.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(error);
        }

        if (
            (this.userRepository.findByEmail(data.email()) != null) ||
            (this.userRepository.findByName(data.name()) != null)
        ) return ResponseEntity.badRequest().body("Username or email already used");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User user = new User(data.name(), data.email(), encryptedPassword, data.role());

        this.userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    /**
     * Authenticates user login.
     *
     * @param data Object containing user credentials
     * @return ResponseEntity containing authentication token
     */
    @SuppressWarnings("rawtypes")
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody AuthenticationDTO data) {
        var credentials = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        
        try {
            var auth = this.authenticationManager.authenticate(credentials);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }
    }

    /**
     * Logsout the user.
     *
     * @param authorizationHeader String containing user token
     * @return ResponseEntity indicating success of logout
     */
    @SuppressWarnings("rawtypes")
    @PostMapping(value = "/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");

        tokenService.revokeToken(token);

        return ResponseEntity.ok().build();
    }

    @SuppressWarnings("rawtypes")
    @GetMapping(value = "/test-api")
    public ResponseEntity test(){
        return ResponseEntity.ok("Hello, World!");
    }

}

