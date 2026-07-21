package com.planner.service;

import com.planner.model.RefreshToken;
import com.planner.model.User;
import com.planner.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository repository;

    public RefreshTokenService(RefreshTokenRepository repository) {
        this.repository = repository;
    }

    public String generateRefreshToken(User user) {
        return UUID.randomUUID().toString();
    }

    public RefreshToken create(User user) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(generateRefreshToken(user))
                .user(user)
                .expiryDate(LocalDateTime.now().plusDays(7))
                .build();

        return repository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return repository.findByToken(token);
    }
}
