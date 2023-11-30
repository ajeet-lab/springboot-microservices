package com.ajeet.blog.ajeetblog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {  

    private int id;
    
    @NotEmpty
    @Size(min = 4, message = "Username must be min of 4 chars")
    private String name;

    @Email(message = "Email is invalid. Please enter the correct email")
    private String email;

    @NotEmpty
    @Size(min = 6, max = 10,message = "Password must be min of 6 chars and max of 10 chars !!")
    private String password;
    
    @NotEmpty
    @Size(min = 10, max = 10, message = "Only 10 digit are allowed")
    private String mobile;
}
