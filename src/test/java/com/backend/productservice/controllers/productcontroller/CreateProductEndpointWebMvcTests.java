package com.backend.productservice.controllers.productcontroller;

import com.backend.productservice.controllers.ProductController;
import com.backend.productservice.dtos.ProductDto;
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

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class CreateProductEndpointWebMvcTests {
    @MockBean
    private ProductService productService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateProductWhenProductIsValid() throws Exception{
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setPrice(10.0);
        product.setDescription("This is a description");
        product.setImage("https://www.google.com");
        product.setTitle("title");
        Category category = new Category();
        category.setId(UUID.randomUUID());
        category.setName("T-shirt");
        product.setCategory(category);

        ProductDto productDto = new ProductDto();
        productDto.setDescription(product.getDescription());
        productDto.setId(product.getId());
        productDto.setPrice(product.getPrice());
        productDto.setImage(product.getImage());
        productDto.setTitle(product.getTitle());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setCategoryName(product.getCategory().getName());

        when(productService.createProduct(anyString(),anyDouble(),anyString(),anyString(),anyString()))
                .thenReturn(product);
        mockMvc.perform(post("/products")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().is(201))
                .andExpect(content().string(objectMapper.writeValueAsString(productDto)));
    }
}
