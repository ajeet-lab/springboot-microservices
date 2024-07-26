package com.ajeet.electronic.store.service;


import com.ajeet.electronic.store.daos.UserDao;
import com.ajeet.electronic.store.dtos.UserDto;
import com.ajeet.electronic.store.entities.User;
import com.ajeet.electronic.store.services.UserService;
import com.ajeet.electronic.store.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;


@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    private Logger log = LoggerFactory.getLogger(UserServiceTest.class);

    @InjectMocks
    UserServiceImpl userServiceImpl;

     @InjectMocks
     UserService userService;

//    @Autowired
//    private UserService userService;

    @MockBean
    private UserDao userDao;


    @Test
    public void validatecreateusertest(){
        /*try{*/
        UserDto userDto = new UserDto(UUID.randomUUID().toString(), "Pihu Maurya", "pihu@gmail.com", "pihu@123","F", "THis is test", "https://cdn.pixabay.com/photo/2021/03/03/08/56/woman-6064819_1280.jpg");

           /* UserDto userDto = UserDto.builder()
                    .userId(UUID.randomUUID().toString())
                    .name("Pihu Maurya")
                    .email("pihu@gmail.com")
                    .password("pihu")
                    .gender("F")
                    .about("THis is test")
                    .imageName("https://cdn.pixabay.com/photo/2021/03/03/08/56/woman-6064819_1280.jpg")
                    .build();*/

           // UserDto userDto1 = userServiceImpl.createUser(userDto);
       Optional<User> userDto1 = userDao.findByEmail("ajeet@gmail.com");
            log.info("User"+ userDto1);
            //Assertions.assertEquals("Pihu Maurya", userDto1.getName());


            // UserDto user = (UserDto) Mockito.when(userService.createUser(userDto)).thenReturn(userDto);
            //userService.createUser(userDto);
            //Assertions.assertEquals(user, userDto);
        /*}catch (Exception ex){
            log.error("Error : " + ex.getMessage());
            log.error("Stace : " + ex.getStackTrace());

        }*/



    }
}
