package com.ajeet.electronic.store.entities;

import lombok.*;

import jakarta.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_email", unique = true)
    private String email;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_gender")
    private String gender;

    @Column(name = "user_about", length = 1000)
    private String about;

    @Column(name = "user_image_name")
    private String imageName;


}
