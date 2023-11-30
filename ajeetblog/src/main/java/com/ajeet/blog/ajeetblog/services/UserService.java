package com.ajeet.blog.ajeetblog.services;

import java.util.List;

import com.ajeet.blog.ajeetblog.payloads.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateUserById(UserDto userDto, Integer userId);

    UserDto getUserById(Integer userId);

    List<UserDto> getAllUser();

    void deleteUserById(Integer userId);
}
