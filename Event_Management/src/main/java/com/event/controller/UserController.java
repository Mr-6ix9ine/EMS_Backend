package com.event.controller;

import com.event.entity.User;
import com.event.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody User loginData) {
        User user = userService.login(loginData.getEmail(), loginData.getPassword());
        if (user != null) return user;
        throw new RuntimeException("Invalid credentials");
    }
}
