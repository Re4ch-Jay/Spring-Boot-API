package com.reach.blog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reach.blog.dto.JwtResponse;
import com.reach.blog.dto.LoginDTO;
import com.reach.blog.dto.RegisterDTO;
import com.reach.blog.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Build Login REST API
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody LoginDTO loginDto) {
        String token = authService.login(loginDto);

        JwtResponse jwtAuthResponse = new JwtResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO) {
        authService.register(registerDTO);

        return ResponseEntity.ok("Successful create new account");

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        authService.logout();

        return ResponseEntity.ok("Successful logout");

    }
}
