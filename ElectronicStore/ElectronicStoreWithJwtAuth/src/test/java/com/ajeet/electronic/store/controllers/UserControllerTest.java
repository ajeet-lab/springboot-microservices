package com.ajeet.electronic.store.controllers;


import com.ajeet.electronic.store.dtos.UserDto;
import com.ajeet.electronic.store.entities.Role;
import com.ajeet.electronic.store.entities.User;
import com.ajeet.electronic.store.helpers.ApiResponse;
import com.ajeet.electronic.store.helpers.PageableResponse;
import com.ajeet.electronic.store.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    String roleId;
    User user;
    Role role;

    String token;

    @BeforeEach
    public void init(){
        token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJpYXQiOjE3MjM4Nzk4MzQsImV4cCI6MTcyMzg5NzgzNH0.KojVeGlvYANQ8UrvbCxOTClEwF4xo1v5B2z1HdswpdzupwxHFw58TP-zjli1qw9UUaqnDuiD7Km1WYf5WCVoMg";
        role = Role.builder().roleId("abc").name("NORMAL").build();
        user = User.builder()
                .name("Ajeet")
                .email("ajeet@gmail.com")
                .about("This is testing create method")
                .gender("Male")
                .imageName("about.png")
                .password("ajeet1234")
                .roles(List.of(role))
                .build();

        roleId = "abc";
    }

    @Test
    public void createUserTest() throws Exception {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(userDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());

    }



    @Test
    public void updateUserTest() throws Exception {
        String userId = "abcdefghijklmn";
        UserDto userDto = UserDto.builder().name("Ajeet Kushwaha")
                .email("ajeetkushwaha@gmail.com")
                .about("This is testing create method")
                .gender("Male")
                .password("ajeet")
                .imageName("ajeet.jpg")
                .build();

        Mockito.when(userService.updateUserById(Mockito.anyString(), Mockito.any())).thenReturn(userDto);


        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/"+userId)
                        // This api is protected with JWT token so We will add a valid token in the Authorization
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());

    }

    @Test
    public void getUserByIdTest() throws Exception {
        String userId = "abcdefghijklmn";
        UserDto userDto = modelMapper.map(user, UserDto.class);

        Mockito.when(userService.getUserById(Mockito.anyString())).thenReturn(userDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/"+userId)
                        // This api is protected with JWT token so We will add a valid token in the Authorization
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void getUserByEmailTest() throws Exception {
        String userId = "ajeet@gmail.com";
        UserDto userDto = modelMapper.map(user, UserDto.class);
        Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(userDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/email/"+userId)
                        // This api is protected with JWT token so We will add a valid token in the Authorization
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }


    @Test
    public void getUserByNameTest() throws Exception {
        String keywords="Amit";
        User user1 = User.builder()
                .name("Amit")
                .email("amit@gmail.com")
                .about("This is testing create method")
                .gender("Male")
                .imageName("amit.png")
                .password("ajeet")
                .roles(List.of(role))
                .build();

        User user2 = User.builder()
                .name("Anish")
                .email("anish@gmail.com")
                .about("This is testing create method")
                .gender("Male")
                .imageName("anish.png")
                .password("ajeet")
                .roles(List.of(role))
                .build();

        List<User> userList = Arrays.asList(user, user1, user2);


        List<UserDto> userDtos = userList.stream().map(u -> modelMapper.map(u, UserDto.class)).collect(Collectors.toList());
        Mockito.when(userService.searchUser(Mockito.anyString())).thenReturn(userDtos);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/search/"+keywords)
                        // This api is protected with JWT token so We will add a valid token in the Authorization
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.name").exists())
        ;
    }

    @Test
    public void getAllUsersTest() throws Exception {

        User user1 = User.builder()
                .name("Amit")
                .email("amit@gmail.com")
                .about("This is testing create method")
                .gender("Male")
                .imageName("amit.png")
                .password("ajeet")
                .roles(List.of(role))
                .build();

        User user2 = User.builder()
                .name("Anish")
                .email("anish@gmail.com")
                .about("This is testing create method")
                .gender("Male")
                .imageName("anish.png")
                .password("ajeet")
                .roles(List.of(role))
                .build();

        List<User> userList = Arrays.asList(user, user1, user2);

        Page<User> page = new PageImpl<>(userList);
        List<UserDto> userDtos = page.stream().map(u -> modelMapper.map(u, UserDto.class)).collect(Collectors.toList());

        PageableResponse<UserDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(userDtos);
        pageableResponse.setPageSize(0);
        pageableResponse.setPageNumber(10);
        pageableResponse.setLastPage(false);
        pageableResponse.setTotalPages(100);
        pageableResponse.setTotalElements(1000);

        Mockito.when(userService.getAllUsers(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/users")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.name").exists())
        ;


    }


    @Test
    public void deleteUserByIdTest() throws IOException {
        String userId="asljflsfljasf";

        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .isSuccess(true)
                .message("User deleted successfully with given user id : "+userId)
                .build();

       // Mockito.when(userService.deleteUser(Mockito.anyString())).thenReturn();

    }




    private String convertObjectToJsonString(User user) throws JsonProcessingException {
        try{
            return new ObjectMapper().writeValueAsString(user);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }



}
