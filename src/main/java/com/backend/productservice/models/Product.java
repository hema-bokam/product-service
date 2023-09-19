package com.backend.productservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "products")
public class Product extends BaseModel{
    private String title;
    private double price;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Category category;
    private String description;
    private String image;  //image url
}
