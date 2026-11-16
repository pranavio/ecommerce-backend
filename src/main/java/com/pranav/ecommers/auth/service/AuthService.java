package com.pranav.ecommers.auth.service;

import com.pranav.ecommers.auth.DTO.LoginRequest;
import com.pranav.ecommers.auth.DTO.LoginResponse;
import com.pranav.ecommers.exception.InvalidCredentialsException;
import com.pranav.ecommers.user.entity.User;
import com.pranav.ecommers.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final  PasswordEncoder passwordEncoder;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user =  userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        LoginResponse response = new LoginResponse();
        response.setUserId(user.getId());
        response.setFirstname(user.getFirstname());
        response.setLastname(user.getLastname());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        return response;

    }
}
