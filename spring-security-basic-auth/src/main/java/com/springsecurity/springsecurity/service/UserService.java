package com.springsecurity.springsecurity.service;

import java.util.List;

import com.springsecurity.springsecurity.entities.User;

public interface UserService {
    List<User> getAllUsers();

    User createUser(User user);

    User updateUser(User user, int id);
}
