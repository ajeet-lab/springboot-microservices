package com.ajeet.electronic.store.helpers;


import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ApiResponse {

    private boolean isSuccess;
    private String message;
    private HttpStatus status;

}
