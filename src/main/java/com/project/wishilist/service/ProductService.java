package com.project.wishilist.service;

import com.project.wishilist.mapper.ProductMapper;
import com.project.wishilist.model.ProductEntity;
import com.project.wishilist.model.dto.request.ProductRequestDto;
import com.project.wishilist.model.dto.response.ProductResponseDto;
import com.project.wishilist.repository.ProductRepository;
import com.project.wishilist.service.interfaces.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) {
        String nameProduct = productRequestDto.getNameProduct();
        String priceProduct = productRequestDto.getPrice();
        String ean  = productRequestDto.getEan();

        logger.info("Cadastrando Produto - nome: " + nameProduct + ", preço: " + priceProduct + ", ean: " + ean);

        ProductEntity productEntity = new ProductEntity(ean, nameProduct, priceProduct);

        productRepository.save(productEntity);

        return productMapper.toResponseDto(productEntity);
    }

}
