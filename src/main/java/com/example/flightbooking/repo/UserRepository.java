package com.example.flightbooking.repo;

import com.example.flightbooking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findOneByEmailAndPassword(String email, String encodedPassword);
    User findByEmail(String email);

    Optional<User> findByUsername(String username);
}

