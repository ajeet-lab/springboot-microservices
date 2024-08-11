package com.ajeet.electronic.store.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {

    private String productId;
    private String title;
    private String description;
    private int price;
    private int discountedPrice;
    private int quantity;
    private Date addedDate;
    @JsonProperty("live")
    private boolean live;
    @JsonProperty("stock")
    private boolean stock;
    private String imageName;
    private CategoryDto category;

}
