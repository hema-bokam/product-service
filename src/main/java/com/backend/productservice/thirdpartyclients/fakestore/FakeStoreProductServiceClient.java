package com.backend.productservice.thirdpartyclients.fakestore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FakeStoreProductServiceClient {
    private RestTemplateBuilder restTemplateBuilder;
    private String fakeStoreUrl;
    private String fakeStoreApiProductPath;
    private String productByIdUrl;
    private String productsUrl;
    public  FakeStoreProductServiceClient(RestTemplateBuilder restTemplateBuilder,
                                          @Value("${fakestore.api.url}") String fakeStoreUrl,
                                          @Value("${fakestore.api.path.products}") String fakeStoreApiProductPath){
        this.restTemplateBuilder = new RestTemplateBuilder();
        this.productsUrl = fakeStoreUrl + fakeStoreApiProductPath;
        this.productByIdUrl  = fakeStoreUrl + fakeStoreApiProductPath + "/{id}";
    }

    public FakeStoreProductDto[] getAllProducts(){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> responseEntity = restTemplate.getForEntity(productsUrl,
                FakeStoreProductDto[].class);
        return responseEntity.getBody();
    }

    public FakeStoreProductDto getProductById(Long id){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.getForEntity(productByIdUrl,
                FakeStoreProductDto.class, id);
        return responseEntity.getBody();
    }

    public FakeStoreProductDto createProduct(FakeStoreProductDto fakeStoreProductDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.postForEntity(productsUrl,
                fakeStoreProductDto, FakeStoreProductDto.class);
        return responseEntity.getBody();
    }

    public FakeStoreProductDto updateProduct(Long id, FakeStoreProductDto fakeStoreProductDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.exchange(productByIdUrl,
                HttpMethod.PUT, new HttpEntity<>(fakeStoreProductDto), FakeStoreProductDto.class, id);
        return responseEntity.getBody();
    }

    public FakeStoreProductDto deleteProduct(Long id){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.exchange(productByIdUrl,
                HttpMethod.DELETE, null, FakeStoreProductDto.class, id);
        return responseEntity.getBody();
    }
}
