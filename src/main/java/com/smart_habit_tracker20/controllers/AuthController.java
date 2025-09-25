package com.smart_habit_tracker20.controllers;

import com.smart_habit_tracker20.DTOs.AuthenticationRequest;

import com.smart_habit_tracker20.DTOs.AuthenticationResponse;
import com.smart_habit_tracker20.models.User;
import com.smart_habit_tracker20.repositories.UserRepository;
import com.smart_habit_tracker20.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String jwt = jwtUtil.generateToken(userDetails.getUsername());

            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    // Optional: get current user info
    // @GetMapping("/me")
    // public ResponseEntity<?> me(Authentication authentication) {
    // if (authentication == null) return ResponseEntity.status(401).build();

    // String email = authentication.getName();
    // User user = userRepository.findByEmail(email).orElse(null);
    // if (user == null) return ResponseEntity.notFound().build();

    // // Return minimal user info (avoid password)
    // return ResponseEntity.ok(new java.util.HashMap<>() {{
    // put("id", user.getId());
    // put("name", user.getName());
    // put("email", user.getEmail());
    // put("provider", user.getProvider());
    // }});
    // }
}
