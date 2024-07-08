package com.ajeet.electronic.store.services.impl;

import com.ajeet.electronic.store.daos.UserDao;
import com.ajeet.electronic.store.dtos.UserDto;
import com.ajeet.electronic.store.entities.User;
import com.ajeet.electronic.store.exceptions.ResourceNotFoundException;
import com.ajeet.electronic.store.helpers.Helper;
import com.ajeet.electronic.store.helpers.PageableResponse;
import com.ajeet.electronic.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${user.profile.image.path}")
    private  String imagePath;

    private static final String NOT_FOUND_BYID="Data not found with given id : ";
    private static final String NOT_FOUND_BYEMAIL="Data not found with given email: ";

   private final UserDao userDao;
   private final ModelMapper modelMapper;


   @Autowired
    public UserServiceImpl(UserDao userDao, ModelMapper modelMapper) {
        this.userDao = userDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        User user = this.modelMapper.map(userDto, User.class);
        User userSaved = this.userDao.save(user);
        return this.modelMapper.map(userSaved, UserDto.class);
    }

    @Override
    public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort =   sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> page = this.userDao.findAll(pageable);

       return Helper.pageableResponse(page, UserDto.class);
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = this.userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_BYID+userId));
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUserById(String userId, UserDto userDto) {
       User user = this.userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_BYID+userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setAbout(userDto.getAbout());
        user.setImageName(userDto.getImageName());

        User updatedUser = this.userDao.save(user);

        return this.modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = this.userDao.findByEmail(email).orElseThrow(()->  new ResourceNotFoundException(NOT_FOUND_BYEMAIL+email));
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = this.userDao.findByNameContaining(keyword);
        return users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(String userId) throws IOException {
        User user = this.userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_BYID+userId));
        String fullPath = imagePath+user.getImageName();
        try{
            Path path = Paths.get(fullPath);
            Files.delete(path);
            this.userDao.delete(user);
            log.info("User deleted with given user id %d: {}", userId);
        }catch (NoSuchFileException ex){
            log.info("NoSuchFileException :: %d: {}", ex.getMessage());
            throw new NoSuchFileException("User image did not found in folder !!");
        } catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }

    }
}
