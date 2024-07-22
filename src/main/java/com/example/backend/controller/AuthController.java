package com.example.backend.controller;

import com.example.backend.model.request.LoginRequest;
import com.example.backend.model.request.RegisterRequest;
import com.example.backend.model.response.LoginResponse;
import com.example.backend.model.response.RegisterResponse;
import com.example.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody final LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public RegisterResponse registerUser(@RequestBody final RegisterRequest request) {
        return authService.register(request);
    }
}
