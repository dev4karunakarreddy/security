package com.security.controller;

import com.security.config.JWTService;
import com.security.exceptions.InvalidToken;
import com.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @PostMapping("/auth/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authorization) {

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing or invalid Authorization header");
        }

        String token = authorization.substring(7);
        String username = jwtService.extractUserName(token);

        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token: no username found");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        try {
            if (jwtService.validateToken(token, userDetails)) {
                return ResponseEntity.ok("Token is valid.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid or expired.");
            }
        } catch (InvalidToken e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token: " + e.getMessage());
        }
    }
}
