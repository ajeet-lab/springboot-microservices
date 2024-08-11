package com.ajeet.electronic.store.services.impl;


import com.ajeet.electronic.store.daos.UserDao;
import com.ajeet.electronic.store.entities.User;
import com.ajeet.electronic.store.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public UserDetailServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userDao.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User not found with given username "+username));
    }
}
