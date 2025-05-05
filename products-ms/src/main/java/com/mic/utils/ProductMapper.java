package com.mic.utils;

import com.mic.dto.ProductDto;
import com.mic.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
    Product productDtoToProduct(ProductDto productDto);
    ProductDto productToProductDto(Product product);
    void updateProduct( ProductDto productDto, @MappingTarget Product product);
    String dtoValueAsString(ProductDto productDto);
}
