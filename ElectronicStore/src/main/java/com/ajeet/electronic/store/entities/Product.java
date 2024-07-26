package com.ajeet.electronic.store.entities;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {

    @Id
    private String productId;
    private String title;
    private String description;
    private int price;
    private int discountedPrice;
    private int quantity;
    private Date addedDate;

    private boolean live;
    private boolean stock;
    private String imageName;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

}
