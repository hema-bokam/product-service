package com.backend.productservice.controllers.productcontroller;

import com.backend.productservice.controllers.ProductController;
import com.backend.productservice.dtos.ProductDto;
import com.backend.productservice.exceptions.ResourceNotFoundException;
import com.backend.productservice.models.Category;
import com.backend.productservice.models.Product;
import com.backend.productservice.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(ProductController.class)
public class GetProductEndpointTests {
    @MockBean
    private ProductService productService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetProductByIdWhenProductIdExists() throws Exception{
        UUID id = UUID.randomUUID();
        Product product = new Product();
        product.setId(id);
        Category category = new Category();
        category.setId(UUID.randomUUID());
        category.setName("T-shirt");
        product.setCategory(category);
        product.setTitle("");
        product.setImage("");
        product.setDescription("");
        when(productService.getProductById(any(String.class)))
                .thenReturn(product);

        ProductDto productDto = new ProductDto();
        productDto.setDescription(product.getDescription());
        productDto.setId(product.getId());
        productDto.setPrice(product.getPrice());
        productDto.setImage(product.getImage());
        productDto.setTitle(product.getTitle());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setCategoryName(product.getCategory().getName());

        mockMvc.perform(get("/products/{id}", id.toString()))
                .andExpect(status().is(200))
                .andExpect(content().string(objectMapper.writeValueAsString(productDto)));
    }

    @Test
    public void testGetProductByIdWhenProductIdDoesNotExist() throws Exception{
        UUID id = UUID.randomUUID();
        when(productService.getProductById(any(String.class)))
                .thenThrow(new ResourceNotFoundException("Product Not found with id: "+id.toString()));

        mockMvc.perform(get("/products/{id}", id.toString()))
                .andExpect(status().is(404));
    }


}
