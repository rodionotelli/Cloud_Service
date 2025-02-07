package com.example.cloudservice.service;

import com.example.cloudservice.dto.LoginRequest;
import com.example.cloudservice.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest loginRequest);

    void logout(String authToken);
}
