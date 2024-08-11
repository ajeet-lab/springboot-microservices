package com.ajeet.electronic.store;


import com.ajeet.electronic.store.daos.RoleDao;
import com.ajeet.electronic.store.daos.UserDao;
import com.ajeet.electronic.store.entities.Role;
import com.ajeet.electronic.store.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class ElectronicStoreApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ElectronicStoreApplication.class, args);
    }


    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        Role role1 = new Role();
        Role role = roleDao.findByName("ROLE_ADMIN").orElse(null);
        if(role == null ){
            role1 = new Role();
            role1.setRoleId(UUID.randomUUID().toString());
            role1.setName("ROLE_ADMIN");
            this.roleDao.save(role1);
            System.out.println("Role is created");
        }

        User user = this.userDao.findByEmail("admin@gmail.com").orElse(null);
        if(user == null){
            user = new User();
            user.setUserId(UUID.randomUUID().toString());
            user.setName("Admin");
            user.setPassword(passwordEncoder.encode("admin123"));
            user.setGender("Male");
            user.setEmail("admin@gmail.com");
            user.setRoles(List.of(role1));
            user.setAbout("This id is admin id who can access all routes");
            user.setImageName("Admin.png");
            userDao.save(user);
            System.out.println("User is created");
        }
    }
}
