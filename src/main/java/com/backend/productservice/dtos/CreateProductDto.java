package com.backend.productservice.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductDto {
    @NotEmpty(message = "Title cannot be empty")
    @Size(min = 2, message = "Title should have at-least 2 characters")
    private String title;
    private double price;
    @NotEmpty(message = "Category cannot be empty")
    @Size(min = 4, message = "Category name should have at-least 4 characters")
    private String categoryName;
    @NotEmpty(message = "Description cannot be empty")
    @Size(min = 4, message = "Description name should have at-least 4 characters")
    private String description;
    private String image;
}
