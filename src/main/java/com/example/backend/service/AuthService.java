package com.example.backend.service;

import com.example.backend.model.request.LoginRequest;
import com.example.backend.model.request.RegisterRequest;
import com.example.backend.model.response.LoginResponse;
import com.example.backend.model.response.RegisterResponse;
import com.example.backend.model.user.User;
import com.example.backend.model.user.UserDetailsImpl;
import com.example.backend.model.user.UserRole;
import com.example.backend.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public LoginResponse login(final LoginRequest request) {

        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = jwtUtils.generateJwtToken(auth);

        return LoginResponse.builder()
                .accessToken(jwt)
                .build();
    }

    public RegisterResponse register(final RegisterRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .role(request.getRole())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
        User savedUser = userService.createNewUser(user);

        return RegisterResponse.builder()
                .email(savedUser.getEmail())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .role(savedUser.getRole())
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .build();
    }
}
