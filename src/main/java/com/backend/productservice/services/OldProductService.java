package com.backend.productservice.services;

import com.backend.productservice.dtos.GetAllGenericProductsResponse;
import com.backend.productservice.dtos.GenericProductDto;
import com.backend.productservice.exceptions.ResourceNotFoundException;

public interface OldProductService {
    GetAllGenericProductsResponse getAllProducts();
    GenericProductDto getProductById(Long id) throws ResourceNotFoundException;
    GenericProductDto createProduct(GenericProductDto genericProductDto);
    GenericProductDto updateProduct(Long id, GenericProductDto genericProductDto);
    GenericProductDto deleteProduct(Long id) throws ResourceNotFoundException;
}
