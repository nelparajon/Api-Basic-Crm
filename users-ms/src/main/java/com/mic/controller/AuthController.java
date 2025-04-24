package com.mic.controller;

import com.mic.dto.AuthRequest;
import com.mic.dto.AuthResponse;
import com.mic.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
private final AuthService authService;

public AuthController(AuthService authService){
    this.authService = authService;
}


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest authRequest) {
        AuthResponse authResponse = authService.authentication(authRequest);
        return ResponseEntity.ok(new AuthResponse(authResponse.getToken()));
    }
}
