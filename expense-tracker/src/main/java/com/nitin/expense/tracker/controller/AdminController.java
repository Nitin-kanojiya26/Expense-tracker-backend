package com.nitin.expense.tracker.controller;

import com.nitin.expense.tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @PutMapping("/users/{userId}/lock")
    public Map<String, Object> lockUser(@PathVariable Long userId) {
        return userService.setUserLocked(userId, true);
    }

    @PutMapping("/users/{userId}/unlock")
    public Map<String, Object> unlockUser(@PathVariable Long userId) {
        return userService.setUserLocked(userId, false);
    }
}
