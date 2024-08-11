package com.ajeet.electronic.store.daos;

import com.ajeet.electronic.store.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleDao extends JpaRepository<Role, String> {

    Optional<Role> findByName(String name);
}
