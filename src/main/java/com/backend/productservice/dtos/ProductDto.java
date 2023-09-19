package com.backend.productservice.dtos;

import com.backend.productservice.models.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductDto {
    private UUID id;
    private String title;
    private double price;
    private String category_name;
    private UUID category_id;
    private String description;
    private String image;
}
