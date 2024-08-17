package com.ajeet.electronic.store.controllers;


import com.ajeet.electronic.store.dtos.UserDto;
import com.ajeet.electronic.store.entities.User;
import com.ajeet.electronic.store.helpers.AppConstents;
import com.ajeet.electronic.store.helpers.JwtRequest;
import com.ajeet.electronic.store.helpers.JwtResponse;
import com.ajeet.electronic.store.security.JwtHelper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);


    private final AuthenticationManager authenticationManager;

    private final JwtHelper jwtHelper;

    private final UserDetailsService userDetailsService;

    private final ModelMapper modelMapper;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtHelper jwtHelper, UserDetailsService userDetailsService, ModelMapper modelMapper){
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
        this.userDetailsService=userDetailsService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){

       this.doAuthenticate(request.getEmail(), request.getPassword());

       // FETCH USER DETAIL : WE CAN USE ONE OF THESE OPTIONS GIVEN BELOW FOR FETCHING USER DATA
        User user = (User)this.userDetailsService.loadUserByUsername(request.getEmail());

        // GENERATE TOKEN
        String token = jwtHelper.generateToken(user);
        logger.info("Token : {}", token);
        // Prepare the JwtResponse
        JwtResponse jwtResponse = JwtResponse.builder().token(token).userDto(modelMapper.map(user, UserDto.class)).build();

        return ResponseEntity.ok(jwtResponse);
    }

    private void doAuthenticate(String email, String password) {
        try{
            Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
            authenticationManager.authenticate(authentication);
        }catch (BadCredentialsException ex){
            throw new BadCredentialsException(AppConstents.INVALID_USERNAME_OR_PASSWORD);
        }
    }


}
