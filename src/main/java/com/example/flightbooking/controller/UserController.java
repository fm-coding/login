package com.example.flightbooking.controller;

import com.example.flightbooking.Dto.LoginDTO;
import com.example.flightbooking.Dto.UserDTO;
import com.example.flightbooking.response.LoginMessage;
import com.example.flightbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveUser(@RequestBody UserDTO userDTO) {
        try {
            userDTO.setPassword(""); // Set an empty password
            String id = userService.addEmployee(userDTO);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error saving user: " + e.getMessage(), e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginMessage> loginUser(@RequestBody LoginDTO loginDTO) {
        try {
            loginDTO.setPassword(""); // Set an empty password
            LoginMessage loginMessage = userService.loginUser(loginDTO);
            return ResponseEntity.ok(loginMessage);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Error logging in: " + e.getMessage(), e);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        try {
            UserDTO userDTO = userService.getUserByUsername(username);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + e.getMessage(), e);
        }
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username) {
        try {
            userService.deleteUserByUsername(username);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + e.getMessage(), e);
        }
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<String> deleteAllUsers() {
        try {
            userService.deleteAllUsers();
            return ResponseEntity.ok("All users deleted successfully");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting users: " + e.getMessage(), e);
        }
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
