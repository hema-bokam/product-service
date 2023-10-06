package com.backend.productservice.controllers.categorycontroller;

import com.backend.productservice.controllers.CategoryController;
import com.backend.productservice.services.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class GetAllCategoriesEndpointWebMvcTests {
    @MockBean
    private CategoryService categoryService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllCategoriesWhenCategoriesExists() throws Exception{
        List<String> categories = List.of("T-shirt", "Shoes");
        when(categoryService.getAllCategories())
                .thenReturn(categories);
        mockMvc.perform(get("/products/categories"))
                .andExpect(status().is(200))
                .andExpect(content().string(objectMapper.writeValueAsString(categories)));
    }
    @Test
    public void testGetAllCategoriesWhenCategoriesDoesNotExist() throws Exception{
        when(categoryService.getAllCategories())
                .thenReturn(List.of());
        mockMvc.perform(get("/products/categories"))
                .andExpect(status().is(200))
                .andExpect(content().string("[]"));
    }
}
