package com.ajeet.electronic.store.services;

import com.ajeet.electronic.store.dtos.UserDto;
import com.ajeet.electronic.store.helpers.PageableResponse;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

    UserDto getUserById(String userId);

    UserDto updateUserById(String userId, UserDto userDto);

    UserDto getUserByEmail(String email);

   List<UserDto> searchUser(String keyword);

   void deleteUser(String userId) throws IOException;

}
