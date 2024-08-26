package com.example.flightbooking.service.impl;

import com.example.flightbooking.Dto.LoginDTO;
import com.example.flightbooking.Dto.UserDTO;
import com.example.flightbooking.entity.User;
import com.example.flightbooking.repo.UserRepository;
import com.example.flightbooking.response.LoginMessage;
import com.example.flightbooking.service.UserService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@NoArgsConstructor
@Service
public class UserImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = (User) userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return convertToDTO(user);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserid(user.getUserid());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        // Don't set the password in the DTO for security reasons
        return dto;
    }

    @Override
    public String addEmployee(UserDTO userDTO) {
        User user = new User(
                userDTO.getUserid(),
                userDTO.getUsername(),
                userDTO.getEmail(),
                this.passwordEncoder.encode(userDTO.getPassword())
        );
        userRepository.save(user);
        return user.getUsername(); // Assuming getUsername() should be getUsername()
    }

    @Override
    public LoginMessage loginUser(LoginDTO loginDTO) {
        User employee = userRepository.findByEmail(loginDTO.getEmail());
        if (employee != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = employee.getPassword();
            boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<User> user = userRepository.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
                if (user.isPresent()) {
                    return new LoginMessage("Login Successful", true);
                } else {
                    return new LoginMessage("Login Failed", false);
                }
            } else {
                return new LoginMessage("Password Does Not Match", false);
            }
        } else {
            return new LoginMessage("Email does not exist", false);
        }
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll(); // Delete all users
    }

    @Override
    public void deleteUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }
}
