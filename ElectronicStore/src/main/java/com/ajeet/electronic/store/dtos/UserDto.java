package com.ajeet.electronic.store.dtos;


import com.ajeet.electronic.store.customvalidator.ImageValid;
import lombok.*;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String userId;

    @Size(min=3, max = 25, message = "Name should be a minimum of 3 and a maximum of 25 characters.")
    private String name;

    @Pattern(regexp = "^(?=.{1,256})(?=.{1,64}@.{1,255})[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",message = "Invalid user email")
    @NotBlank(message = "Email should not be blank")
    private String email;

    @Size(min=6, max = 25, message = "Password should be a minimum of 6 and a maximum of 8 characters.")
    @NotBlank(message = "Password should not be blank")
    private String password;

    @NotBlank(message = "Gender should not be blank")
    @Size(min=4, max = 8, message = "Gender should be a minimum of 4 and a maximum of 8 characters.")
    private String gender;

    @NotBlank(message = "About should not be blank")
    private String about;

    @ImageValid
    private String imageName;
}
