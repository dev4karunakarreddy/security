package com.security.services;

import com.security.Entity.Users;
import com.security.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UserRepository userRepository;

    public Users create(Users user){
        return userRepository.save(user);
    }
}
