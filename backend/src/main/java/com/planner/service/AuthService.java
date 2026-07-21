    package com.planner.service;

    import com.planner.dto.AuthResponseDTO;
    import com.planner.dto.UserRequestDTO;
    import com.planner.exceptions.InvalidPasswordException;
    import com.planner.exceptions.UserAlreadyExistsException;
    import com.planner.exceptions.UserNotFoundException;
    import com.planner.model.RefreshToken;
    import com.planner.model.User;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.stereotype.Service;

    import java.time.LocalDateTime;

    @Service
    public class AuthService {
        private final UserService userService;
        private final BCryptPasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final RefreshTokenService refreshTokenService;

        public AuthService(UserService userService, BCryptPasswordEncoder passwordEncoder, JwtService jwtService, RefreshTokenService refreshTokenService) {
            this.userService = userService;
            this.passwordEncoder = passwordEncoder;
            this.jwtService = jwtService;
            this.refreshTokenService = refreshTokenService;
        }

        public AuthResponseDTO register(UserRequestDTO dto) {
            if (userService.findByEmail(dto.getEmail()).isPresent()) {
                throw new UserAlreadyExistsException("User with this email already exists.");
            }

            User user = userService.create(dto);
            RefreshToken refreshToken = refreshTokenService.create(user);

            return new AuthResponseDTO(jwtService.generateToken(user.getEmail()), refreshToken.getToken());
        }

        public AuthResponseDTO login(UserRequestDTO dto) {
            User user = userService.findByEmail(dto.getEmail()).orElseThrow(() ->  new UserNotFoundException("User not found."));
            if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
                throw new InvalidPasswordException("Wrong password.");
            }

            RefreshToken refreshToken = refreshTokenService.create(user);

            return new AuthResponseDTO(jwtService.generateToken(user.getEmail()), refreshToken.getToken());
        }

        public AuthResponseDTO refresh(String refreshToken) {
            RefreshToken token = refreshTokenService.findByToken(refreshToken)
                    .orElseThrow(() -> new RuntimeException("Refresh token not found."));

            if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Refresh token expired.");
            }

            String newAccessToken = jwtService.generateToken(token.getUser().getEmail());
            return new AuthResponseDTO(newAccessToken, refreshToken);
        }
    }
