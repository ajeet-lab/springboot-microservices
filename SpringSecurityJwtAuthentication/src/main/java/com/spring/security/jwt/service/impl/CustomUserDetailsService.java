package com.spring.security.jwt.service.impl;

import com.spring.security.jwt.entities.User;
import com.spring.security.jwt.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = this.userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found Exception"));
        return user;
    }
}
