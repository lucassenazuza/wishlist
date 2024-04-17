package com.project.wishilist.service;

import com.project.wishilist.config.BadRequestException;
import com.project.wishilist.config.NotFoundException;
import com.project.wishilist.mapper.ProductMapper;
import com.project.wishilist.model.ProductEntity;
import com.project.wishilist.model.UserEntity;
import com.project.wishilist.model.WishListEntity;
import com.project.wishilist.model.dto.request.WishListRequestDto;
import com.project.wishilist.model.dto.request.WishListRequestDto;
import com.project.wishilist.model.dto.response.ProductResponseDto;
import com.project.wishilist.model.dto.response.WishListResponseDto;
import com.project.wishilist.repository.ProductRepository;
import com.project.wishilist.repository.UserRepository;
import com.project.wishilist.repository.WishListRepository;
import com.project.wishilist.service.interfaces.IWishListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class WishListService implements IWishListService {

    private static final Logger logger = LoggerFactory.getLogger(WishListService.class);

    @Autowired
    WishListRepository wishListRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductMapper productMapper;


    public void addWishList(WishListRequestDto wishListRequestDto) throws BadRequestException, NotFoundException, Exception {

        String productCode = wishListRequestDto.getProductCode();
        String ean = wishListRequestDto.getEan();
        String email = wishListRequestDto.getEmail();
        String userCode = wishListRequestDto.getUserCode();

        //Verifica se foi informado (product_code ou ean), e se foi informado (email ou user_code)
        if (productCode == null && ean == null)
            throw new BadRequestException("Informar product_code ou ean!");
        if (email == null && userCode == null)
            throw new BadRequestException("Informar email ou user_code!");

        //Busca Produto na Base
        ProductEntity productEntity = searchProductEntity(productCode, ean);

        //Busca Usuario na Base
        UserEntity userEntity = searchUserEntity(email, userCode);

        logger.info("Adicionando produto: " + productEntity.getNameProduct() + "(codigo ean: "
                + productEntity.getEan() + ") a wishilist do usuario " + userEntity.getEmail());

        //Verifica Tamanho da WishList atual
        WishListEntity wishListEntity = wishListRepository.findByUserEntity(userEntity);

        if (wishListEntity != null && wishListEntity.getProductEntities().size() >= 20) {
            throw new Exception("Numero Maximo de Produtos na Lista de desejos (Wishlist) atingido");
        }

        if (wishListEntity == null) {
            List<ProductEntity> productEntityList = Arrays.asList(productEntity);

            wishListEntity = WishListEntity.builder()
                    .userEntity(userEntity)
                    .productEntities(productEntityList)
                    .build();

        } else {
            List<ProductEntity> newList = new ArrayList<>(wishListEntity.getProductEntities());
            newList.add(productEntity);
            wishListEntity.setProductEntities(newList);
        }

        wishListRepository.save(wishListEntity);
    }

    public void deleteProductAtWishList(String email, String userCode, String productCode, String ean) throws BadRequestException, NotFoundException {
        if (email == null && userCode == null)
            throw new BadRequestException("Informar user_code ou email");
        if (productCode == null && ean == null)
            throw new BadRequestException("Informar product_code ou ean");

        UserEntity userEntity = searchUserEntity(email, userCode);
        ProductEntity productEntity = searchProductEntity(productCode, ean);

        WishListEntity wishListEntity = wishListRepository.findByUserEntity(userEntity);

        if (wishListEntity != null) {
            List<ProductEntity> productEntityList = wishListEntity.getProductEntities();

            //Buscando produto
            productEntityList = productEntityList.stream()
                    .filter(item -> !Objects.equals(item.getProductCode(), productEntity.getProductCode()))
                    .collect(Collectors.toList());

            wishListEntity.setProductEntities(productEntityList);

            wishListRepository.save(wishListEntity);

        } else {
            throw new NotFoundException("Produto não está na Wishlist do usuário");
        }
    }

    public WishListResponseDto searchProductWishList(String email, String userCode, String productCode, String ean) throws BadRequestException, NotFoundException {
        if (email == null && userCode == null)
            throw new BadRequestException("Informar user_code ou email");
        if (productCode == null && ean == null)
            throw new BadRequestException("Informar product_code ou ean");

        UserEntity userEntity = searchUserEntity(email, userCode);
        ProductEntity productEntity = searchProductEntity(productCode, ean);

        WishListEntity wishListEntity = wishListRepository.findByUserEntity(userEntity);
        List<ProductEntity> productEntities = null;

        if (wishListEntity != null) {
            productEntities = wishListEntity.getProductEntities();

            //Buscando produto
            productEntities = productEntities.stream()
                    .filter(item -> item.getProductCode().equals(productEntity.getProductCode()))
                    .collect(Collectors.toList());

        } else {
            throw new NotFoundException("Produto não está na Wishlist do usuário");
        }

        List<ProductResponseDto> productResponseDtos = (productEntities == null) ? null : productMapper.toResponseDtolist(productEntities);

        return WishListResponseDto.builder()
                .email(userEntity.getEmail())
                .userCode(userEntity.getUserCode())
                .products(productResponseDtos)
                .build();
    }

    public WishListResponseDto searchAllProducts(String email, String userCode) throws BadRequestException, NotFoundException {

        if (email == null && userCode == null)
            throw new BadRequestException("Informar user_code ou email");

        UserEntity userEntity = searchUserEntity(email, userCode);

        WishListEntity wishListEntity = wishListRepository.findByUserEntity(userEntity);

        List<ProductResponseDto> productResponseDtos = null;

        if (wishListEntity != null) {
            productResponseDtos = productMapper.toResponseDtolist(wishListEntity.getProductEntities());
        }

        return WishListResponseDto.builder()
                .email(userEntity.getEmail())
                .userCode(userEntity.getUserCode())
                .products(productResponseDtos)
                .build();
    }

    private ProductEntity searchProductEntity(String productCode, String ean) throws NotFoundException, BadRequestException {

        ProductEntity productEntity = productRepository.findByProductCodeOrEan(productCode, ean);

        if (productEntity == null)
            throw new NotFoundException("Produto não encontrado na base!");

        return productEntity;
    }

    private UserEntity searchUserEntity(String email, String userCode) throws NotFoundException, BadRequestException {

        UserEntity userEntity = userRepository.findByEmailOrUserCode(email, userCode);

        if (userEntity == null)
            throw new NotFoundException("Usuário não encontrado!");

        return userEntity;
    }
}
