package com.example.flightbooking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    private String email;
    private String password;

    public Throwable getUsername() {
        return null;
    }
}