package com.backend.productservice.controllers.productcontroller;

import com.backend.productservice.controllers.ProductController;
import com.backend.productservice.dtos.DeleteProductResponse;
import com.backend.productservice.exceptions.ResourceNotFoundException;
import com.backend.productservice.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class DeleteProductEndpointWebMvcTests {
    @MockBean
    private ProductService productService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void testDeleteProductWhenProductIdExists() throws Exception{
        UUID id = UUID.randomUUID();
        doNothing().when(productService).deleteProduct(anyString());
        DeleteProductResponse deleteProductResponse = new DeleteProductResponse();
        deleteProductResponse.setMessage("Product deleted successfully");
        mockMvc.perform(delete("/products/{id}", id.toString()))
                .andExpect(status().is(200))
                .andExpect(content().string(objectMapper.writeValueAsString(deleteProductResponse)));
    }
    @Test
    public void testDeleteProductWhenProductIdDoesNotExist() throws Exception{
        UUID id = UUID.randomUUID();
        doThrow(new ResourceNotFoundException("Product not found with id: " + id.toString()))
                .when(productService).deleteProduct(id.toString());
        mockMvc.perform(delete("/products/{id}", id.toString()))
                .andExpect(status().is(404));
    }

}
