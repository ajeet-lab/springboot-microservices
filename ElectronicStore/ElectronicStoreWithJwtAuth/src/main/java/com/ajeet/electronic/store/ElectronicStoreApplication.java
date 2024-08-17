package com.ajeet.electronic.store;


import com.ajeet.electronic.store.daos.RoleDao;
import com.ajeet.electronic.store.daos.UserDao;
import com.ajeet.electronic.store.entities.Role;
import com.ajeet.electronic.store.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class ElectronicStoreApplication implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(ElectronicStoreApplication.class);
    private final UserDao userDao;

    private final RoleDao roleDao;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ElectronicStoreApplication(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder){
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder=passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(ElectronicStoreApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        Role role1 = new Role();
        Role role = this.roleDao.findByName("ROLE_ADMIN").orElse(null);
        if(role == null ){
            role1 = new Role();
            role1.setRoleId(UUID.randomUUID().toString());
            role1.setName("ROLE_ADMIN");
            this.roleDao.save(role1);
            log.info("Role is created");
        }

        User user = this.userDao.findByEmail("admin@gmail.com").orElse(null);
        String pass = "admin123";
        if(user == null){
            user = new User();
            user.setUserId(UUID.randomUUID().toString());
            user.setName("Admin");
            user.setPassword(this.passwordEncoder.encode(pass));
            user.setGender("Male");
            user.setEmail("admin@gmail.com");
            user.setRoles(List.of(role1));
            user.setAbout("This id is admin id who can access all routes");
            user.setImageName("Admin.png");
            this.userDao.save(user);
            log.info("User is created");
        }
    }
}
