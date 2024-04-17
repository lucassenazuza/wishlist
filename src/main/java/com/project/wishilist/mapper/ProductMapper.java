package com.project.wishilist.mapper;


import com.project.wishilist.model.ProductEntity;
import com.project.wishilist.model.dto.response.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "nameProduct", target = "name")
    @Mapping(source = "priceProduct", target = "price")
    ProductResponseDto toResponseDto(ProductEntity productEntity);

    @Mapping(source = "nameProduct", target = "name")
    @Mapping(source = "priceProduct", target = "price")
    List<ProductResponseDto> toResponseDtolist(List<ProductEntity> productEntity);

}
