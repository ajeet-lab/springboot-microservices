package com.ajeet.electronic.store.helpers;



import lombok.*;


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
