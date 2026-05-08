package com.nitin.expense.tracker.controller;

import com.nitin.expense.tracker.dto.LoginRequest;
import com.nitin.expense.tracker.dto.RegisterRequest;
import com.nitin.expense.tracker.dto.UserDTO;
import com.nitin.expense.tracker.entity.User;
import com.nitin.expense.tracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public Map<String, Object> login(@Valid @RequestBody LoginRequest credentials) {
        return userService.loginUser(credentials.getUsername().trim(), credentials.getPassword());
    }

    @PostMapping("/register")
    public Map<String, Object> register(@Valid @RequestBody RegisterRequest request) {
        Map<String, Object> response = new HashMap<>();

        try {
            User user = new User();
            user.setUsername(request.getUsername().trim());
            user.setPassword(request.getPassword());
            user.setEmail(request.getEmail().trim());
            user.setFullName(request.getFullName().trim());

            UserDTO savedUser = userService.registerUser(user);

            response.put("success", true);
            response.put("message", "Registration successful!");
            response.put("userId", savedUser.getId());
            response.put("username", savedUser.getUsername());
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
        }

        return response;
    }
}
