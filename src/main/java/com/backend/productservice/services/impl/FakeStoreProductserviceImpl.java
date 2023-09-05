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

@Service
public class FakeStoreProductserviceImpl implements ProductService {
    private ModelMapper mapper;
    private RestTemplateBuilder restTemplateBuilder;
    private String getProductByIdUrl = "https://fakestoreapi.com/products/{id}";
    private String productsUrl = "https://fakestoreapi.com/products";
    private String updateProductUrl = "https://fakestoreapi.com/products/{id}";
    private String deleteProductUrl = "https://fakestoreapi.com/products/{id}";
    public  FakeStoreProductserviceImpl(ModelMapper mapper,
                                        RestTemplateBuilder restTemplateBuilder){
        this.mapper = mapper;
        this.restTemplateBuilder = new RestTemplateBuilder();
    }

    @Override
    public GetAllProductsResponse getAllProducts(){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<GetAllProductsResponse> response = restTemplate.exchange(productsUrl,
                HttpMethod.GET, null, GetAllProductsResponse.class);
        return response.getBody();
    }

    @Override
    public GenericProductDto getProductById(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.getForEntity(getProductByIdUrl,
                FakeStoreProductDto.class, id);
        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        GenericProductDto genericProductDto = mapToGenericDto(fakeStoreProductDto);
        return genericProductDto;
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto genericProductDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<GenericProductDto> response = restTemplate.postForEntity(productsUrl,
                genericProductDto, GenericProductDto.class);
        return response.getBody();
    }

    @Override
    public GenericProductDto updateProduct(Long id, GenericProductDto genericProductDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<GenericProductDto> response = restTemplate.exchange(updateProductUrl,
                HttpMethod.PUT, new HttpEntity<>(genericProductDto), GenericProductDto.class, id);
        return response.getBody();
    }

    @Override
    public GenericProductDto deleteProduct(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.delete(deleteProductUrl, id);
        ResponseEntity<GenericProductDto> response = restTemplate.exchange(deleteProductUrl,
                HttpMethod.DELETE, new HttpEntity<>(new GenericProductDto()), GenericProductDto.class, id);
        return response.getBody();
    }

    private GenericProductDto mapToGenericDto(FakeStoreProductDto fakeStoreProductDto) {
        return this.mapper.map(fakeStoreProductDto, GenericProductDto.class);
    }
}
