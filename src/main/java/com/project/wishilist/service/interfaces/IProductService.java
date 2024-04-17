package com.project.wishilist.service.interfaces;

import com.project.wishilist.model.dto.request.ProductRequestDto;
import com.project.wishilist.model.dto.response.ProductResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public interface IProductService {
    ProductResponseDto addProduct(ProductRequestDto productRequestDto);
}
