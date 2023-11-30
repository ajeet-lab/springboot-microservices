package com.springsecurity.springsecurity.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springsecurity.springsecurity.entities.User;
import com.springsecurity.springsecurity.repos.UserRepo;
import com.springsecurity.springsecurity.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        return users;
    }

    @Override
    public User createUser(User user) {
       user.setPassword(this.passwordEncoder.encode(user.getPassword()));
       return this.userRepo.save(user);
    }

    @Override
    public User updateUser(User user, int id) {
        User getUser = this.userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found :: "+id));
        getUser.setRoles(user.getRoles());
        User updatedUser = this.userRepo.save(getUser);
        return updatedUser;
    }
    
}
