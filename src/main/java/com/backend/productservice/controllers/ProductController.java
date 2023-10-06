package com.backend.productservice.controllers;

import com.backend.productservice.dtos.CreateProductDto;
import com.backend.productservice.dtos.DeleteProductResponse;
import com.backend.productservice.dtos.ProductDto;
import com.backend.productservice.models.Category;
import com.backend.productservice.models.Product;
import com.backend.productservice.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") String id){
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(mapToProductDto(product), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){

        List<Product> products = productService.getAllProducts();
        List<ProductDto> productDtos = products.stream().map(
                product -> mapToProductDto(product))
                .collect(Collectors.toList());
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid  @RequestBody CreateProductDto request){
        Product product = productService.createProduct(request.getTitle(),
                request.getPrice(),
                request.getCategoryName(),
                request.getDescription(),
                request.getImage());
        return new ResponseEntity<>(mapToProductDto(product), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String id,
                                           @RequestBody ProductDto request){
        Product product = productService.updateProduct(id,
                request.getTitle(),
                request.getPrice(),
                request.getCategoryName(),
                request.getDescription(),
                request.getImage());
        ProductDto productDto = mapToProductDto(product);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteProductResponse> deleteProduct(@PathVariable("id") String id){
        productService.deleteProduct(id);
        DeleteProductResponse response = new DeleteProductResponse();
        response.setMessage("Product deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
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
