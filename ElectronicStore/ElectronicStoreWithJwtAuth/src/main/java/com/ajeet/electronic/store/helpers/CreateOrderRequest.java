package com.ajeet.electronic.store.helpers;


import com.ajeet.electronic.store.dtos.OrderItemDto;
import com.ajeet.electronic.store.dtos.UserDto;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CreateOrderRequest {
    private String cartId;
    private String userId;
    private String orderStatus="PENDING";
    private String paymentStatus="NOT_PAID";
    private String billingAddress;
    private String billingPhone;
    private String billingName;
}
