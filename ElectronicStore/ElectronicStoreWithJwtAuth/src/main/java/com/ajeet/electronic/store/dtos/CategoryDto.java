package com.ajeet.electronic.store.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private String categoryId;
    private String title;
    private String description;
    private String coverImage;
}
