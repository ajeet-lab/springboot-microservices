package com.ajeet.electronic.store.controllers;

import com.ajeet.electronic.store.dtos.UserDto;
import com.ajeet.electronic.store.helpers.ApiResponse;
import com.ajeet.electronic.store.helpers.ImageApiResponse;
import com.ajeet.electronic.store.helpers.PageableResponse;
import com.ajeet.electronic.store.services.FileService;
import com.ajeet.electronic.store.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final FileService fileService;

    @Value("${user.profile.image.path}")
    private String imagePath;

    @Autowired
    public UserController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }


    // http://localhost:9902/api/v1/users/
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto userDto1 = this.userService.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    // http://localhost:9902/api/v1/users/
    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<UserDto> userDtos = this.userService.getAllUsers(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        UserDto userDto1 = this.userService.getUserById(userId);
        return new ResponseEntity<>(userDto1, HttpStatus.OK);
    }


    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUserById(@Valid @PathVariable String userId, @RequestBody UserDto userDto) {
        UserDto userDto1 = this.userService.updateUserById(userId, userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        UserDto userDto = this.userService.getUserByEmail(email);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }


    @GetMapping("/search/{name}")
    public ResponseEntity<List<UserDto>> getUserByName(@PathVariable String name) {
        List<UserDto> userDtos = this.userService.searchUser(name);
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUserById(@PathVariable String userId) throws IOException {
        try{
            this.userService.deleteUser(userId);
            ApiResponse apiResponse = ApiResponse.builder().isSuccess(true).message("User deleted successfully with given user id : " + userId).status(HttpStatus.OK).build();
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }catch (IOException ex){
            ApiResponse apiResponse = ApiResponse.builder().isSuccess(false).message(ex.getMessage()).status(HttpStatus.NOT_FOUND).build();
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }catch (InvalidPathException ex){
            ApiResponse apiResponse = ApiResponse.builder().isSuccess(false).message("InvalidPathException ::  "+ex.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageApiResponse> uploadImage(@RequestParam("userImageName") MultipartFile file, @PathVariable("userId") String userId) throws IOException {
        String uploadImaged = this.fileService.uploadFile(file, imagePath);

        UserDto userDto = this.userService.getUserById(userId);

        String fullPath = imagePath+userDto.getImageName();
        Path path = Paths.get(fullPath);
        // Before the update, we check if the images are available or not.
        if(Files.exists(path)){
            Files.delete(path);
        }
        // Update the imageName in database with given user Id
        userDto.setImageName(uploadImaged);
        this.userService.updateUserById(userId, userDto);

        ImageApiResponse imageApiResponse = ImageApiResponse.builder().isSuccess(true).message("File uploaded successfully with Image name : " + uploadImaged).imageName(uploadImaged).status(HttpStatus.OK).build();
        return new ResponseEntity<>(imageApiResponse, HttpStatus.OK);
    }

    @GetMapping("/images/{userId}")
    public void serverImages(@PathVariable("userId") String userId, HttpServletResponse response) throws IOException {
        UserDto userDto = userService.getUserById(userId);
        log.info("UserController_001 :: userImageName {}", userDto.getImageName());

       InputStream resource = fileService.getResource(imagePath, userDto.getImageName());
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
