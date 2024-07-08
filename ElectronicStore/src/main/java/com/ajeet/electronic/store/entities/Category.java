package com.ajeet.electronic.store.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category {

    @Id
    private String categoryId;

    @Column(name = "category_title", length = 60, nullable = false)
    private String title;

    @Column(name = "category_description", length = 50)
    private String description;

    @Column(name = "category_cover_image")
    private String coverImage;

}
