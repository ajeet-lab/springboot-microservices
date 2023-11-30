package com.ajeet.blog.ajeetblog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {  
    private int categoryId;
    @NotEmpty(message = "Category Name should be not empty")
    @Size(min = 3, message = "Category should be min of 4 chars")
    private String categoryName;

    @NotEmpty(message = "Category Description should be not empty")
    @Size(min = 3, message = "Category Description be min of 4 chars")
    private String CategoryDescription;
}
