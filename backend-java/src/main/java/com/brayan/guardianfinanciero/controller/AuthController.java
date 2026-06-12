package com.brayan.guardianfinanciero.controller;

import com.brayan.guardianfinanciero.dto.auth.AuthResponse;
import com.brayan.guardianfinanciero.dto.auth.LoginRequest;
import com.brayan.guardianfinanciero.dto.auth.RegisterRequest;
import com.brayan.guardianfinanciero.dto.auth.UserResponse;
import com.brayan.guardianfinanciero.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me() {
        return ResponseEntity.ok(authService.getCurrentUser());
    }
}
