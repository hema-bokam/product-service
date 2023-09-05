package com.backend.productservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product extends BaseModel{
    private String title;
    private int price;
    private String category;
    private String description;
    private String image;  //image url
}
