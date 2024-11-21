package com.security.controller;

import com.security.Entity.Users;
import com.security.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UsersService usersService;


    @PostMapping("/register")
    public Users users(@RequestBody Users user){
        return usersService.create(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> logIn(@RequestBody Users user){
        String token = usersService.verify(user);
        if (token.equals("failed to login")) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(
                token,
                HttpStatus.OK);
    }
}
