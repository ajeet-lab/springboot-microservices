package com.springsecurity.springsecurity.repos;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.springsecurity.springsecurity.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByName(String username);
}
