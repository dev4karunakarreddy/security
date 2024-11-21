package com.security.controller;

import com.security.Entity.Users;
import com.security.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UsersService usersService;

    BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder(12);

    @PostMapping("/register")
    public Users users(@RequestBody Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersService.create(user);
    }
}
