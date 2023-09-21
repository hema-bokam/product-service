package com.backend.productservice.dtos;

import com.backend.productservice.models.Category;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
public class ProductDto {
    private UUID id;
    private String title;
    private double price;
    private String categoryName;
    private UUID categoryId;
    private String description;
    private String image;
}
