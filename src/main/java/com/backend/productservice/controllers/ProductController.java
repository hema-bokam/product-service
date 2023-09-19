package com.backend.productservice.controllers;

import com.backend.productservice.dtos.DeleteProductResponse;
import com.backend.productservice.dtos.ProductDto;
import com.backend.productservice.models.Product;
import com.backend.productservice.services.ProductService;
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
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") UUID id){
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(mapToProductDto(product), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts(){

        List<Product> products = productService.getAllProducts();
        List<ProductDto> productDtos = products.stream().map(
                product -> mapToProductDto(product))
                .collect(Collectors.toList());
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        Product product = productService.createProduct(productDto.getTitle(),
                productDto.getPrice(),
                productDto.getCategory_name(),
                productDto.getDescription(),
                productDto.getImage());
        return new ResponseEntity<>(mapToProductDto(product), HttpStatus.OK);
    }
//    @PutMapping("/{id}")
//    public GenericProductDto updateProduct(@PathVariable UUID id,
//                                           @RequestBody ProductDto productDto){
//        return productService.updateProduct(id, productDto);
//    }
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteProductResponse> deleteProduct(@PathVariable("id") UUID id){
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
        productDto.setCategory_id(product.getCategory().getId());
        productDto.setCategory_name(product.getCategory().getName());
        return productDto;
    }
}
