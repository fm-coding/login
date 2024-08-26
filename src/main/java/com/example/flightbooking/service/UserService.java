package com.example.flightbooking.service;

import com.example.flightbooking.Dto.LoginDTO;
import com.example.flightbooking.Dto.UserDTO;
import com.example.flightbooking.response.LoginMessage;

public interface UserService {
    UserDTO getUserByUsername(String username);
    String addEmployee(UserDTO userDTO);
    LoginMessage loginUser (LoginDTO loginDTO);

    void deleteAllUsers();

    void deleteUserByUsername(String username);
}
