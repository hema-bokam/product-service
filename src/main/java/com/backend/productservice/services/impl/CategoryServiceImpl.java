package com.backend.productservice.services.impl;

import com.backend.productservice.exceptions.ResourceNotFoundException;
import com.backend.productservice.models.Category;
import com.backend.productservice.models.Product;
import com.backend.productservice.repositories.CategoryRepository;
import com.backend.productservice.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<String> getAllCategories() {
        return categoryRepository.findAllNames();
    }

    @Override
    public List<Product> getProductsByCategory(String uuid) {
        Category category = categoryRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: "+uuid));
        return category.getProducts();
    }
}
