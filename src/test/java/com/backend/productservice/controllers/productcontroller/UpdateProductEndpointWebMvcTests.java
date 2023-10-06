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

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class UpdateProductEndpointWebMvcTests {
    @MockBean
    private ProductService productService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void testUpdateProductWhenProductExists() throws Exception{
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

        when(productService.updateProduct(anyString(),anyString(),anyDouble(),anyString(),anyString(),anyString()))
                .thenReturn(product);

        ProductDto productDto = new ProductDto();
        productDto.setDescription(product.getDescription());
        productDto.setId(product.getId());
        productDto.setPrice(product.getPrice());
        productDto.setImage(product.getImage());
        productDto.setTitle(product.getTitle());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setCategoryName(product.getCategory().getName());

        mockMvc.perform(put("/products/{id}", id.toString())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().is(200))
                .andExpect(content().string(objectMapper.writeValueAsString(productDto)));
    }

    @Test
    public void testUpdateProductWhenProductDoesNotExist() throws Exception{
        when(productService.updateProduct(anyString(),anyString(),anyDouble(),anyString(),anyString(),anyString()))
                .thenThrow(new ResourceNotFoundException("Product with id: not found"));
        mockMvc.perform(put("/products/{id}", UUID.randomUUID().toString())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new ProductDto())))
                .andExpect(status().is(404));
    }
}
