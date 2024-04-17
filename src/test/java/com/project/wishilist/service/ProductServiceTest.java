package com.project.wishilist.service;

import com.project.wishilist.mapper.ProductMapper;
import com.project.wishilist.model.ProductEntity;
import com.project.wishilist.model.dto.request.ProductRequestDto;
import com.project.wishilist.model.dto.response.ProductResponseDto;
import com.project.wishilist.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;
    @Mock
    ProductMapper productMapper;

    ProductRequestDto productRequestDto;
    ProductEntity productEntity;
    ProductResponseDto productResponseDto;

    @BeforeEach
    void setUp() {
        productRequestDto = new ProductRequestDto("Produto Teste", "250.50", "1234567891234");
        productEntity = new ProductEntity("1234567891234", "Produto Teste", "1234567891234");
        productResponseDto = new ProductResponseDto(productEntity);
    }

    @Test
    void addProductSucess() {
        //Given When Then

        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);
        when(productMapper.toResponseDto(any(ProductEntity.class))).thenReturn(productResponseDto);

        ProductResponseDto productResponseDtoTest = productService.addProduct(productRequestDto);

        verify(productRepository).save(any(ProductEntity.class));
        assertEquals(productResponseDtoTest, productResponseDto);
    }
}