package com.spring.security.jwt.jwt;

import com.spring.security.jwt.utils.UserDto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private UserDto userDto;
}
