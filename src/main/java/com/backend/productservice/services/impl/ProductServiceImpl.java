package com.backend.productservice.services.impl;

import com.backend.productservice.exceptions.ResourceNotFoundException;
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
    public Product getProductById(String uuid) {
        Optional<Product> productOptional = productRepository.findById(UUID.fromString(uuid));
        if(productOptional.isEmpty()){
            throw new ResourceNotFoundException("Product Not found with id: "+uuid);
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
        Optional<Category> optionalCategory= categoryRepository.findByNameEqualsIgnoreCase(categoryName);
        if(optionalCategory.isPresent()){
            product.setCategory(optionalCategory.get());
        }
        else{
            Category category = new Category();
            category.setName(categoryName);
            product.setCategory(categoryRepository.save(category));
        }
        return productRepository.save(product);
    }


    @Override
    public Product updateProduct(String uuid, String title, double price, String categoryName,String description,String image) {
        Product product = productRepository.findById(UUID.fromString(uuid)).orElseThrow(()
                -> new ResourceNotFoundException("Product Not found with id: "+uuid));

        if(title != null && !title.isBlank()) product.setTitle(title);
        if(price > 0) product.setPrice(price);
        if(description != null && !description.isBlank()) product.setDescription(description);
        if(image != null && !image.isBlank()) product.setImage(image);
        if(categoryName != null && !categoryName.isBlank()){
            Optional<Category> optionalCategory= categoryRepository.findByNameEqualsIgnoreCase(categoryName);
            if(optionalCategory.isPresent()){
                product.setCategory(optionalCategory.get());
            }
            else{
                Category category = new Category();
                category.setName(categoryName);
                product.setCategory(categoryRepository.save(category));
            }
        }
        Product savedProduct = productRepository.save(product);
        return product;
    }

    @Override
    public void deleteProduct(String uuid) {
        Product product = productRepository.findById(UUID.fromString(uuid)).orElseThrow(()
        -> new ResourceNotFoundException("Product not found with id: "+uuid));
        productRepository.delete(product);
    }


}
