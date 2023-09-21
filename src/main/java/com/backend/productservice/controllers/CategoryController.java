package com.backend.productservice.controllers;

import com.backend.productservice.dtos.ProductDto;
import com.backend.productservice.models.Product;
import com.backend.productservice.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products/categories")
public class CategoryController {
    private CategoryService categoryService;
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }
    @GetMapping
    public ResponseEntity<List<String>> getAllCategories(){
        List<String> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable("id") String id){
        List<Product> products = categoryService.getProductsByCategory(id);
        List<ProductDto> productDtos = new ArrayList<>();
        products.forEach(
                product -> {
                    productDtos.add(mapToProductDto(product));
                }
        );
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }
    private ProductDto mapToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setDescription(product.getDescription());
        productDto.setId(product.getId());
        productDto.setPrice(product.getPrice());
        productDto.setImage(product.getImage());
        productDto.setTitle(product.getTitle());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setCategoryName(product.getCategory().getName());
        return productDto;
    }
}
