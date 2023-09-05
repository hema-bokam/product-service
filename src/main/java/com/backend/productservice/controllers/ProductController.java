package com.backend.productservice.controllers;

import com.backend.productservice.dtos.GenericProductDto;
import com.backend.productservice.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService;
    }
    @GetMapping("/{id}")
    public GenericProductDto getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }
    @GetMapping
    public List<GenericProductDto> getProducts(){
        return productService.getAllProducts().getGenericProductDtos();
    }
    @PostMapping
    public GenericProductDto createProduct(@RequestBody GenericProductDto genericProductDto){
        return productService.createProduct(genericProductDto);
    }
    @PutMapping("/{id}")
    public GenericProductDto updateProduct(@PathVariable Long id,
                                           @RequestBody GenericProductDto genericProductDto){
        return productService.updateProduct(id, genericProductDto);
    }
    @DeleteMapping("/{id}")
    public GenericProductDto deleteProduct(@PathVariable Long id){
        return productService.deleteProduct(id);
    }
}
