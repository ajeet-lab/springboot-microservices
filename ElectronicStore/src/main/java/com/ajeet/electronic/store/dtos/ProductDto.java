package com.ajeet.electronic.store.dtos;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private String productId;
    private String title;
    private String description;
    private int price;
    private int discountedPrice;
    private int quantity;
    private Date addedDate;
    private boolean isLive;
    private boolean isStock;
}
