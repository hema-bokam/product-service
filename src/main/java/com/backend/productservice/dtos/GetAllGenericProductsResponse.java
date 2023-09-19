package com.backend.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetAllGenericProductsResponse {
    private List<GenericProductDto> genericProductDtos;
}
