package com.project.wishilist.controller;

import com.project.wishilist.model.dto.request.ProductRequestDto;
import com.project.wishilist.model.dto.response.DataResponse;
import com.project.wishilist.model.dto.response.ProductResponseDto;
import com.project.wishilist.service.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping
    public ResponseEntity<DataResponse<ProductResponseDto>> addProduct(@RequestBody @Valid ProductRequestDto productRequestDto){

        ProductResponseDto productResponseDto = productService.addProduct(productRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new DataResponse<ProductResponseDto>(productResponseDto));
    }
}
