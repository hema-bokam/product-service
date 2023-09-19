package com.backend.productservice.services.impl;

import com.backend.productservice.exceptions.ProductNotFoundException;
import com.backend.productservice.models.Category;
import com.backend.productservice.models.Product;
import com.backend.productservice.repositories.CategoryRepository;
import com.backend.productservice.repositories.ProductRepository;
import com.backend.productservice.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    public ProductServiceImpl(ProductRepository productRepository,
                              ModelMapper mapper,
                              CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Product getProductById(UUID id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty()){
            throw new ProductNotFoundException("Product Not found with id: "+id);
        }
        return productOptional.get();
    }
    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    @Override
    public Product createProduct(String title, double price, String categoryName, String description, String image) {
        Product product = new Product();
        product.setTitle(title);
        product.setPrice(price);
        product.setDescription(description);
        product.setImage(image);
        Category category = new Category();
        category.setName(categoryName);
        Category savedCategory = categoryRepository.save(category);
        product.setCategory(savedCategory);

        return productRepository.save(product);
    }


    @Override
    public Product updateProduct(UUID id, Product product) {
        Product product1 = productRepository.findById(id).orElseThrow(()
                -> new ProductNotFoundException("Product Not found with id: "+id));
        //product1.setCategory();
        return null;
    }

    @Override
    public void deleteProduct(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(()
        -> new ProductNotFoundException("Product not found with id: "+id));
        productRepository.delete(product);
    }


}
