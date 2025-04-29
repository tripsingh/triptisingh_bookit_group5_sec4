package com.bookit.controller;

import com.bookit.dto.user.UserRequestDTO;
import com.bookit.dto.user.UserResponseDTO;
import com.bookit.entity.User;
import com.bookit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public UserResponseDTO createUser(@RequestBody UserRequestDTO dto) {
        User user = userService.createUser(dto);
        return new UserResponseDTO(
                user.getUserId(), user.getFirstName(), user.getLastName(),
                user.getEmail(), user.getPhoneNumber(), user.getUserType()
        );
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(u -> new UserResponseDTO(
                        u.getUserId(), u.getFirstName(), u.getLastName(),
                        u.getEmail(), u.getPhoneNumber(), u.getUserType()
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public UserResponseDTO getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return new UserResponseDTO(
                user.getUserId(), user.getFirstName(), user.getLastName(),
                user.getEmail(), user.getPhoneNumber(), user.getUserType()
        );
    }
}
