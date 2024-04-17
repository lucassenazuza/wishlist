package com.project.wishilist.controller;

import com.project.wishilist.config.BadRequestException;
import com.project.wishilist.config.NotFoundException;
import com.project.wishilist.model.dto.request.WishListRequestDto;
import com.project.wishilist.model.dto.response.DataResponse;
import com.project.wishilist.model.dto.response.WishListResponseDto;
import com.project.wishilist.service.interfaces.IWishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    private IWishListService wishListService;

    @PostMapping
    public ResponseEntity<DataResponse<String>> addWishList(@RequestBody WishListRequestDto wishlistListRequestDto)
            throws BadRequestException, Exception {

        wishListService.addWishList(wishlistListRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new DataResponse<String>("Adicionado a lista de desejo(wishlist) com sucesso"));
    }

    @GetMapping("/allProducts")
    public ResponseEntity<DataResponse<WishListResponseDto>> searchAllProducts(@RequestParam(name = "email", required=false) String email,
                                                                               @RequestParam(name = "user_code", required=false) String userCode)
            throws BadRequestException, NotFoundException {

        WishListResponseDto wishListResponseDto = wishListService.searchAllProducts(email, userCode);

        return ResponseEntity.status(HttpStatus.OK).body(new DataResponse<>(wishListResponseDto));
    }
    @GetMapping("/product")
    public ResponseEntity<DataResponse<WishListResponseDto>> searchProductWishList(@RequestParam(name = "email", required=false) String email,
                                                                                    @RequestParam(name = "user_code", required=false) String userCode,
                                                                                    @RequestParam(name = "product_code", required=false) String productCode,
                                                                                    @RequestParam(name = "ean", required=false) String ean)

            throws BadRequestException, NotFoundException {

        WishListResponseDto wishListResponseDto = wishListService.searchProductWishList(email, userCode, productCode, ean);

        return ResponseEntity.status(HttpStatus.OK).body(new DataResponse<>(wishListResponseDto));
    }
    @DeleteMapping("/product")
    public ResponseEntity<DataResponse<String>> deleteProductAtWishList(@RequestParam(name = "email", required=false) String email,
                                                                                   @RequestParam(name = "user_code", required=false) String userCode,
                                                                                   @RequestParam(name = "product_code", required=false) String productCode,
                                                                                   @RequestParam(name = "ean", required=false) String ean)

            throws BadRequestException, NotFoundException {

        wishListService.deleteProductAtWishList(email, userCode, productCode, ean);

        return ResponseEntity.status(HttpStatus.OK).body(new DataResponse<String>("Removido da lista de desejo(wishlist) com sucesso"));
    }
}

