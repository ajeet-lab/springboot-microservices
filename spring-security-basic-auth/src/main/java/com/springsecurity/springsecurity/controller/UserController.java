package com.springsecurity.springsecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.springsecurity.entities.User;
import com.springsecurity.springsecurity.service.UserService;

@RestController
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/api/v1/users")
    public List<User> getAllUsers(){
        List<User> users = this.userService.getAllUsers();
        return users;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/v1/users/create")   
    public User createUser(@RequestBody User user){
        return this.userService.createUser(user);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/api/v1/users/update/{id}")   
    public User updateUser(@RequestBody User user, @PathVariable int id){
        return this.userService.updateUser(user, id);
    }

}
