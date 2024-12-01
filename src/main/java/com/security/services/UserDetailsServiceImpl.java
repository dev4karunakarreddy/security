package com.security.services;

import com.security.Entity.Users;
import com.security.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user=userRepository.findByName(username);
        if(null==user) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User Not Found");
        }
//        it requires UserDetailsType we create our own class which implements UserDetails
        return new UserDetailsImpl(user);
    }
}
