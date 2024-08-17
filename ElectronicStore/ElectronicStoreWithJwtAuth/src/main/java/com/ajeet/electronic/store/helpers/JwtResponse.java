package com.ajeet.electronic.store.helpers;


import com.ajeet.electronic.store.dtos.UserDto;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
    private String token;
    private UserDto userDto;
    private String refreshToken;
}
