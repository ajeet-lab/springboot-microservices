package com.spring.security.jwt.service;

import com.spring.security.jwt.entities.User;
import com.spring.security.jwt.utils.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
}
