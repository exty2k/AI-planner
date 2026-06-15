package com.planner.controller;

import com.planner.dto.UserRequestDTO;
import com.planner.dto.UserResponseDTO;
import com.planner.model.User;
import com.planner.repository.UserRepository;
import com.planner.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/users")
    public UserResponseDTO create(@RequestBody UserRequestDTO userRequestDTO) {
        User user = userService.create(userRequestDTO);

        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setCreatedAt(user.getCreatedAt());

        return response;
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
