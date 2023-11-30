package com.spring.security.jwt.service.impl;

import com.spring.security.jwt.entities.User;
import com.spring.security.jwt.repos.UserRepo;
import com.spring.security.jwt.service.UserService;
import com.spring.security.jwt.utils.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserDto> getAllUsers() {
       List<User> users = this.userRepo.findAll();
       List<UserDto> userDtos = users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }


    // Convert User to UserDto
    private UserDto UserToUserDto(User user){
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }

    // Convert UserDto to User
    private User UserDtoToUser(UserDto userDto){
        User user = this.modelMapper.map(userDto, User.class);
        return user;
    }


}
