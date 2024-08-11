package com.ajeet.electronic.store.dtos;


import lombok.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {
    private String cartId;
    // If You are required User details, you can uncomment UserDto
    // private UserDto user
    private Date createdAt;
    private List<CartItemDto> items = new ArrayList<>();
}
