package com.backend.productservice.services.impl;

import com.backend.productservice.thirdpartyclients.fakestore.FakeStoreProductDto;
import com.backend.productservice.dtos.GetAllGenericProductsResponse;
import com.backend.productservice.dtos.GenericProductDto;
import com.backend.productservice.exceptions.ResourceNotFoundException;
import com.backend.productservice.services.OldProductService;
import com.backend.productservice.thirdpartyclients.fakestore.FakeStoreProductServiceClient;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FakeStoreProductServiceImpl implements OldProductService {
    private ModelMapper mapper;
    private FakeStoreProductServiceClient fakeStoreProductServiceClient;
    public FakeStoreProductServiceImpl(ModelMapper mapper,
                                       FakeStoreProductServiceClient fakeStoreProductServiceClient){
        this.mapper = mapper;
        this.fakeStoreProductServiceClient = fakeStoreProductServiceClient;
    }

    @Override
    public GetAllGenericProductsResponse getAllProducts(){
        FakeStoreProductDto[] fakeStoreProductDtos = fakeStoreProductServiceClient.getAllProducts();
        List<GenericProductDto> products = Arrays.stream(fakeStoreProductDtos)
                .map(fakeStoreProductDto -> mapToGenericProductDto(fakeStoreProductDto))
                .collect(Collectors.toList());
        GetAllGenericProductsResponse response = new GetAllGenericProductsResponse();
        response.setGenericProductDtos(products);
        return response;
    }

    @Override
    public GenericProductDto getProductById(Long id){
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductServiceClient.getProductById(id);
        if(fakeStoreProductDto == null){
            throw new ResourceNotFoundException("Product Not found with id: "+id);
        }
        return mapToGenericProductDto(fakeStoreProductDto);
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto genericProductDto) {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductServiceClient
                .createProduct(mapToFakeStoreProductDto(genericProductDto));
        return mapToGenericProductDto(fakeStoreProductDto);
    }

    @Override
    public GenericProductDto updateProduct(Long id, GenericProductDto genericProductDto) {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductServiceClient
                .updateProduct(id, mapToFakeStoreProductDto(genericProductDto));
        return mapToGenericProductDto(fakeStoreProductDto);
    }

    @Override
    public GenericProductDto deleteProduct(Long id){
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductServiceClient
                .deleteProduct(id);
        if(fakeStoreProductDto == null){
            throw new ResourceNotFoundException("Product Not found with id: "+id);
        }
        return mapToGenericProductDto(fakeStoreProductDto);
    }

    private GenericProductDto mapToGenericProductDto(FakeStoreProductDto fakeStoreProductDto) {
        return this.mapper.map(fakeStoreProductDto, GenericProductDto.class);
    }
    private FakeStoreProductDto mapToFakeStoreProductDto(GenericProductDto genericProductDto){
        return this.mapper.map(genericProductDto, FakeStoreProductDto.class);
    }
}
