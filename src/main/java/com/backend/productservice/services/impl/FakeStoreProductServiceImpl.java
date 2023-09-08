package com.backend.productservice.services.impl;

import com.backend.productservice.dtos.FakeStoreProductDto;
import com.backend.productservice.dtos.GetAllProductsResponse;
import com.backend.productservice.dtos.GenericProductDto;
import com.backend.productservice.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FakeStoreProductServiceImpl implements ProductService {
    private ModelMapper mapper;
    private RestTemplateBuilder restTemplateBuilder;
    private String productByIdUrl = "https://fakestoreapi.com/products/{id}";
    private String productsUrl = "https://fakestoreapi.com/products";
    public  FakeStoreProductServiceImpl(ModelMapper mapper,
                                        RestTemplateBuilder restTemplateBuilder){
        this.mapper = mapper;
        this.restTemplateBuilder = new RestTemplateBuilder();
    }

    @Override
    public GetAllProductsResponse getAllProducts(){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> responseEntity = restTemplate.getForEntity(productByIdUrl,
                FakeStoreProductDto[].class);
        List<GenericProductDto> products = Arrays.stream(responseEntity.getBody())
                .map(fakeStoreProductDto -> mapToGenericDto(fakeStoreProductDto))
                .collect(Collectors.toList());
        GetAllProductsResponse response = new GetAllProductsResponse();
        response.setGenericProductDtos(products);
        return response;
    }

    @Override
    public GenericProductDto getProductById(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.getForEntity(productByIdUrl,
                FakeStoreProductDto.class, id);
        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        GenericProductDto genericProductDto = mapToGenericDto(fakeStoreProductDto);
        return genericProductDto;
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto genericProductDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.postForEntity(productsUrl,
                genericProductDto, FakeStoreProductDto.class);
        GenericProductDto product = mapToGenericDto(response.getBody());
        return product;
    }

    @Override
    public GenericProductDto updateProduct(Long id, GenericProductDto genericProductDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.exchange(productByIdUrl,
                HttpMethod.PUT, new HttpEntity<>(genericProductDto), FakeStoreProductDto.class, id);
        GenericProductDto updatedProduct = mapToGenericDto(response.getBody());
        return updatedProduct;
    }

    @Override
    public GenericProductDto deleteProduct(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.exchange(productByIdUrl,
                HttpMethod.DELETE, null, FakeStoreProductDto.class, id);
        GenericProductDto deletedProduct = mapToGenericDto(response.getBody());
        return deletedProduct;
    }

    private GenericProductDto mapToGenericDto(FakeStoreProductDto fakeStoreProductDto) {
        return this.mapper.map(fakeStoreProductDto, GenericProductDto.class);
    }
}
