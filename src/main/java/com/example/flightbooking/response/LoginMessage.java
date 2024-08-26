package com.example.flightbooking.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Data
@NoArgsConstructor
public class LoginMessage {
    String message;
    Boolean status;

    public LoginMessage(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }
}
