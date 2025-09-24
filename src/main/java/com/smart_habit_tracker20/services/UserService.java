package com.smart_habit_tracker20.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smart_habit_tracker20.DTOs.UserRegistrationRequest;
import com.smart_habit_tracker20.DTOs.UserResponse;
import com.smart_habit_tracker20.models.User;
import com.smart_habit_tracker20.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // injected automatically

    public UserResponse registerUser(UserRegistrationRequest request) {
        // Check if email exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Map DTO → Entity
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // 🔒 Encode password before saving
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Save
        User savedUser = userRepository.save(user);

        // Map Entity → DTO
        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());

        return response;
    }
}
