package com.pranav.ecommers.auth.controller;

import com.pranav.ecommers.auth.DTO.LoginRequest;
import com.pranav.ecommers.auth.DTO.LoginResponse;
import com.pranav.ecommers.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
            LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);

    }
}
