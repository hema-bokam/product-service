package com.backend.productservice.services;

import com.backend.productservice.dtos.GetAllProductsResponse;
import com.backend.productservice.dtos.GenericProductDto;

public interface ProductService {
    GetAllProductsResponse getAllProducts();
    GenericProductDto getProductById(Long id);
    GenericProductDto createProduct(GenericProductDto genericProductDto);
    GenericProductDto updateProduct(Long id, GenericProductDto genericProductDto);
    GenericProductDto deleteProduct(Long id);
}
