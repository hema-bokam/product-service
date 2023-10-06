package com.backend.productservice.controllers.categorycontroller;

import com.backend.productservice.controllers.CategoryController;
import com.backend.productservice.dtos.ProductDto;
import com.backend.productservice.exceptions.ResourceNotFoundException;
import com.backend.productservice.models.Category;
import com.backend.productservice.models.Product;
import com.backend.productservice.services.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class GetProductsByCategoryEndPointWebMvcTests {
    @MockBean
    private CategoryService categoryService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void testGetProductsByCategoryWhenCategoryExists() throws Exception{
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setTitle("Grey T-shirt");
        product.setImage("");
        product.setDescription("");
        Category category = new Category();
        category.setId(UUID.randomUUID());
        category.setName("T-shirt");
        product.setCategory(category);
        products.add(product);

        when(categoryService.getProductsByCategory(anyString()))
                .thenReturn(products);
        List<ProductDto> productDtos = products.stream().map(product1 -> {
                                            ProductDto productDto = new ProductDto();
                                            productDto.setId(product1.getId());
                                            productDto.setCategoryId(product1.getCategory().getId());
                                            productDto.setCategoryName(product1.getCategory().getName());
                                            productDto.setPrice(product1.getPrice());
                                            productDto.setTitle(product1.getTitle());
                                            productDto.setDescription(product1.getDescription());
                                            productDto.setImage(product1.getImage());
                                            return productDto;
                                        }).collect(Collectors.toList());

        mockMvc.perform(get("/products/categories/{id}", category.getId()))
                .andExpect(status().is(200))
                .andExpect(content().string(objectMapper.writeValueAsString(productDtos)));

    }
    @Test
    public void testGetProductsByCategoryWhenCategoryExistsButNoProducts() throws Exception{
        when(categoryService.getProductsByCategory(anyString()))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get("/products/categories/{id}", UUID.randomUUID().toString()))
                .andExpect(status().is(200))
                .andExpect(content().string("[]"));

    }
    @Test
    public void testGetProductsByCategoryWhenCategoryDoesNotExist() throws Exception{
        UUID id = UUID.randomUUID();
        when(categoryService.getProductsByCategory(anyString()))
                .thenThrow(new ResourceNotFoundException("Category Not found with id: "+id.toString()));

        mockMvc.perform(get("/products/categories/{id}", id.toString()))
                .andExpect(status().is(404));
    }
}
