package com.planner.controller;

import com.planner.dto.AuthResponseDTO;
import com.planner.dto.UserRequestDTO;
import com.planner.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponseDTO register(@Valid @RequestBody UserRequestDTO dto) {
         return new AuthResponseDTO(authService.register(dto));
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@Valid @RequestBody UserRequestDTO dto) {
        return new AuthResponseDTO(authService.login(dto));
    }
}
