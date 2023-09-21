package com.backend.productservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "products")
public class Product extends BaseModel{
    @Column(nullable = false)
    private String title;
    private double price;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false, name = "category_id")
    private Category category;
    private String description;
    private String image;  //image url
}
