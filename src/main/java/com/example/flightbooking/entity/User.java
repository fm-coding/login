package com.example.flightbooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userid;

    private String username;
    private String email;
    private String password;
}
