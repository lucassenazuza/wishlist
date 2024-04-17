package com.project.wishilist.service.interfaces;

import com.project.wishilist.config.BadRequestException;
import com.project.wishilist.config.NotFoundException;
import com.project.wishilist.model.dto.request.WishListRequestDto;
import com.project.wishilist.model.dto.response.WishListResponseDto;
import org.springframework.stereotype.Service;


@Service
public interface IWishListService {

    void addWishList(WishListRequestDto wishListRequestDto) throws BadRequestException, NotFoundException, Exception;
    void deleteProductAtWishList(String email, String userCode, String productCode, String ean) throws BadRequestException, NotFoundException;
    WishListResponseDto searchProductWishList(String email, String userCode, String productCode, String ean) throws BadRequestException, NotFoundException;
    WishListResponseDto searchAllProducts(String email, String userCode) throws BadRequestException, NotFoundException;
}
