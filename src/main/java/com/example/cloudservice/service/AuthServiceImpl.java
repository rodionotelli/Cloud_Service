package com.example.cloudservice.service;

import com.example.cloudservice.dto.LoginRequest;
import com.example.cloudservice.dto.LoginResponse;
import com.example.cloudservice.jwt.JwtGenerator;
import com.example.cloudservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LogManager.getLogger(AuthServiceImpl.class);
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final UserRepository userRepository;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        final var authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.login(), loginRequest.password());
        final var authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final var token = jwtGenerator.generateToken(authentication);
        log.info("User is logged in");
        return new LoginResponse(token);
    }

    @Override
    public void logout(String authToken) {
        userRepository.findByUsername(authToken);
        log.info("User logout");
        userRepository.removeByLogin(authToken);
    }
}
