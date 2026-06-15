    package com.planner.service;

    import com.planner.dto.UserRequestDTO;
    import com.planner.exceptions.InvalidPasswordException;
    import com.planner.exceptions.UserAlreadyExistsException;
    import com.planner.exceptions.UserNotFoundException;
    import com.planner.model.User;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.stereotype.Service;

    @Service
    public class AuthService {
        private final UserService userService;
        private final BCryptPasswordEncoder passwordEncoder;
        private final JwtService jwtService;

        public AuthService(UserService userService, BCryptPasswordEncoder passwordEncoder, JwtService jwtService) {
            this.userService = userService;
            this.passwordEncoder = passwordEncoder;
            this.jwtService = jwtService;
        }

        public String register(UserRequestDTO dto) {
            if (userService.findByEmail(dto.getEmail()).isPresent()) {
                throw new UserAlreadyExistsException("User with this email already exists.");
            }
            else {
                userService.create(dto);
            }
            return jwtService.generateToken(dto.getEmail());
        }

        public String login(UserRequestDTO dto) {
            User user = userService.findByEmail(dto.getEmail()).orElseThrow(() ->  new UserNotFoundException("User not found."));
            if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
                throw new InvalidPasswordException("Wrong password.");
            }

            return jwtService.generateToken(dto.getEmail());
        }
    }
