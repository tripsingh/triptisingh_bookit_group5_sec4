// src/main/java/com/bookit/service/UserService.java
package com.bookit.service;

import com.bookit.dto.user.UserRequestDTO;
import com.bookit.entity.User;
import com.bookit.entity.UserType;
import com.bookit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new user based on the DTO.
     */
    public User createUser(UserRequestDTO dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPasswordHash(dto.getPasswordHash());
        user.setUserType(UserType.valueOf(dto.getUserType()));
        return userRepository.save(user);
    }

    /**
     * Fetches all users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Fetches a user by their ID.
     */
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }
}
