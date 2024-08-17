package com.ajeet.electronic.store.services;

import com.ajeet.electronic.store.daos.RoleDao;
import com.ajeet.electronic.store.daos.UserDao;
import com.ajeet.electronic.store.dtos.UserDto;
import com.ajeet.electronic.store.entities.Role;
import com.ajeet.electronic.store.entities.User;
import com.ajeet.electronic.store.helpers.PageableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.*;


@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserDao userDao;

    @MockBean
    private RoleDao roleDao;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;


    User user;
    Role role;
    String roleId;

    @BeforeEach
    public void init() {
        role = Role.builder().roleId("abc").name("NORMAL").build();
        user = User.builder()
                .name("Ajeet")
                .email("ajeet@gmail.com")
                .about("This is testing create method")
                .gender("Male")
                .imageName("about.png")
                .password("ajeet")
                .roles(List.of(role))
                .build();

        roleId = "abc";
    }

    @Test
    public void createUserTest() {
        Mockito.when(userDao.save(Mockito.any())).thenReturn(user);
        Mockito.when(roleDao.findById(roleId)).thenReturn(Optional.of(role));

        UserDto user1 = userService.createUser(mapper.map(user, UserDto.class));
        System.out.println("User name : " + user1.getName());

        Assertions.assertNotNull(user1);
        Assertions.assertEquals("Ajeet", user1.getName());
    }


    @Test
    public void updateUserTest() {
        String userId = "abcdefghijklmn";
        UserDto userDto = UserDto.builder().name("Ajeet Kushwaha")
                .email("ajeet@gmail.com")
                .about("This is testing create method")
                .gender("Male")
                .password("ajeet")
                .imageName("ajeet.jpg")
                .build();

        Mockito.when(userDao.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userDao.save(Mockito.any())).thenReturn(user);

        UserDto updatedUser = userService.updateUserById(userId, userDto);
        // UserDto updatedUser = userDto; // Generate an error
        System.out.println("Updated user name: " + updatedUser.getName());
        System.out.println("Updated user image name: " + updatedUser.getImageName());
        System.out.println("Before update user name : " + user.getName());

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(user.getName(), updatedUser.getName());

    }

    @Test
    public void deleteUserTest() throws IOException {
        String userId = "lsjflksjflalfsjsldfjsaf";
        Mockito.when(userDao.findById(userId)).thenReturn(Optional.of(user));

        // Before deleting user make sure that user image will have to present in /images/users folder
        userService.deleteUser(userId);
        Mockito.verify(userDao, Mockito.times(1)).delete(user);
    }

    @Test
    public void getAllUserTest() {

        User user1 = User.builder()
                .name("Ashish Maurya")
                .email("ajeet@gmail.com")
                .about("This is testing create method")
                .gender("Male")
                .imageName("about.png")
                .password("ajeet")
                .roles(List.of(role))
                .build();

        User user2 = User.builder()
                .name("Ajay Maurya")
                .email("ajeet@gmail.com")
                .about("This is testing create method")
                .gender("Male")
                .imageName("about.png")
                .password("ajeet")
                .roles(List.of(role))
                .build();

        List<User> userList = Arrays.asList(user, user1, user2);
        Page<User> page = new PageImpl<>(userList);
        Mockito.when(userDao.findAll((Pageable) Mockito.any())).thenReturn(page);

        PageableResponse<UserDto> userDto = userService.getAllUsers(0, 4, "name", "desc");
        Assertions.assertEquals(3, userDto.getContent().size());
    }

    @Test
    public void getUserByIdTest(){
        String userId="alsjflsjfljslkfjslkf";

        Mockito.when(userDao.findById(userId)).thenReturn(Optional.of(user));
      UserDto userDto =  userService.getUserById(userId);
        System.out.println(user.getName());
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getName(), userDto.getName());
    }

    @Test
    public void getUserByEmailTest(){
        String email="ajeet@gmail.com";
        Mockito.when(userDao.findByEmail(email)).thenReturn(Optional.of(user));
        UserDto userDto =  userService.getUserByEmail(email);
        System.out.println(user.getName());
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getName(), userDto.getName());
    }

    @Test
    public void searchUserTest(){
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

        String keywords="Amit";

        Mockito.when(userDao.findByNameContaining(keywords)).thenReturn(userList);
        List<UserDto> userDtos =  userService.searchUser(keywords);

        Assertions.assertEquals(3, userDtos.size());
    }

}
