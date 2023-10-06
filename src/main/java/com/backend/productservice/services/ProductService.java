package com.backend.productservice.services;

import com.backend.productservice.models.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(String uuid);
    List<Product> getAllProducts();
    Product createProduct(String title, double price, String categoryName,String description,String image);
    Product updateProduct(String uuid, String title, double price, String categoryName,String description,String image);
    void deleteProduct(String uuid);
}
