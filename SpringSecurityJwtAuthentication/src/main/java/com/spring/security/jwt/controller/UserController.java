package com.spring.security.jwt.controller;

import com.spring.security.jwt.entities.User;
import com.spring.security.jwt.exception.BadApiRequestException;
import com.spring.security.jwt.jwt.JwtHelper;
import com.spring.security.jwt.jwt.JwtRequest;
import com.spring.security.jwt.jwt.JwtResponse;
import com.spring.security.jwt.service.UserService;
import com.spring.security.jwt.utils.UserDto;
import jakarta.servlet.ServletException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private static Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
   private AuthenticationManager manager;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest req){
        doAuthentication(req.getUsername(), req.getPassword());
        log.info("Recived data from body : {} "+ req);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(req.getUsername());
        UserDto userDto = this.modelMapper.map(userDetails, UserDto.class);
        String token = this.helper.generateToken(userDetails);
        JwtResponse jwtResponse = JwtResponse.builder().token(token).userDto(userDto).build();
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    private void doAuthentication(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try{
            manager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            throw new BadApiRequestException("Username or password is invailid");
        }
    }


    @GetMapping("/users")
    public List<UserDto> getAllUsers(){
        return this.userService.getAllUsers();
    }

    @GetMapping("/current-user")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(principal.getName());
        UserDto userDto = this.modelMapper.map(userDetails, UserDto.class);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @ExceptionHandler(value = {ServletException.class})
    public String servletException(ServletException e){
        log.info("ServletException ===== "+ e.getMessage());
        return e.getMessage();
    }
}
