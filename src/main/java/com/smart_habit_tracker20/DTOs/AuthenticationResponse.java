package com.smart_habit_tracker20.DTOs;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private String token;
    private String tokenType = "Bearer";

    public AuthenticationResponse(String token) {
        this.token = token;
    }
}
