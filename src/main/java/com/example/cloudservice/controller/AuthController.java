package com.example.cloudservice.controller;

import com.example.cloudservice.dto.LoginRequest;
import com.example.cloudservice.dto.LoginResponse;
import org.springframework.http.ResponseEntity;

public interface AuthController {
    ResponseEntity<LoginResponse> login(LoginRequest loginRequest);
    ResponseEntity<Void> logout(String authToken);
}
