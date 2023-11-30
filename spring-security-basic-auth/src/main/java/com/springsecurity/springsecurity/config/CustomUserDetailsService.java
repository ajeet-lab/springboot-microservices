package com.springsecurity.springsecurity.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.springsecurity.springsecurity.entities.User;
import com.springsecurity.springsecurity.repos.UserRepo;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {       
        Optional<User> user = this.userRepo.findByName(username);
        return user.map(User::new).orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

}
