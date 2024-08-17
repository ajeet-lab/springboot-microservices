package com.ajeet.electronic.store.helpers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
@AllArgsConstructor
@Builder
public class ImageApiResponse {

    private boolean isSuccess;
    private String message;
    private String imageName;
    private HttpStatus status;
}
