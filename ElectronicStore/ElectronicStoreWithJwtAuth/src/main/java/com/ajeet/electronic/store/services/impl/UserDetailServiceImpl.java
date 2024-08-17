package com.ajeet.electronic.store.services.impl;


import com.ajeet.electronic.store.daos.UserDao;
import com.ajeet.electronic.store.exceptions.ResourceNotFoundException;
import com.ajeet.electronic.store.helpers.AppConstents;
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
       return userDao.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException(AppConstents.USER_NOT_FOUND_BY_EMAIL_ID+username));
    }
}
