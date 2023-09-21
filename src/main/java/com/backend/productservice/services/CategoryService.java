package com.backend.productservice.services;

import com.backend.productservice.models.Category;
import com.backend.productservice.models.Product;

import java.util.List;

public interface CategoryService {
    List<String> getAllCategories();
    List<Product> getProductsByCategory(String uuid);
}
