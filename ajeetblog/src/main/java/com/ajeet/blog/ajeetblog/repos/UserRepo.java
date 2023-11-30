package com.ajeet.blog.ajeetblog.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajeet.blog.ajeetblog.entites.User;

public interface UserRepo extends JpaRepository<User, Integer>{
    
}
