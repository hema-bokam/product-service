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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@WebMvcTest(ProductController.class)
public class GetAllProductsEndpointWebMvcTests {
    @MockBean
    private ProductService productService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllProductsWhenProductsExists() throws Exception{
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

        List<Product> products = new ArrayList<>();
        products.add(product);
        when(productService.getAllProducts())
                .thenReturn(products);

        List<ProductDto> productDtos = getProductDtos(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().is(200))
                .andExpect(content().string(objectMapper.writeValueAsString(productDtos)));
    }
    @Test
    public void testGetAllProductsWhenProductsDoesNotExist() throws Exception{
        when(productService.getAllProducts())
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get("/products"))
                .andExpect(status().is(200))
                .andExpect(content().string("[]"));
    }

    private List<ProductDto> getProductDtos(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setDescription(product.getDescription());
            productDto.setId(product.getId());
            productDto.setPrice(product.getPrice());
            productDto.setImage(product.getImage());
            productDto.setTitle(product.getTitle());
            productDto.setCategoryId(product.getCategory().getId());
            productDto.setCategoryName(product.getCategory().getName());
            productDtos.add(productDto);
        }
        return productDtos;
    }

}
