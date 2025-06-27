package com.event.controller;


import com.event.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    private final JwtUtil jwtService;

    public AdminController(JwtUtil jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestParam String email, @RequestParam String password) {
        if (email.equals(adminEmail) && password.equals(adminPassword)) {
            String token = jwtService.generateToken(email);
            return ResponseEntity.ok().body("Bearer " + token);
        } else {
            return ResponseEntity.status(401).body("Invalid admin credentials");
        }
    }
}