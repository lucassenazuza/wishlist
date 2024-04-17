package com.project.wishilist.service;

import com.project.wishilist.config.BadRequestException;
import com.project.wishilist.config.NotFoundException;
import com.project.wishilist.mapper.ProductMapper;
import com.project.wishilist.model.ProductEntity;
import com.project.wishilist.model.UserEntity;
import com.project.wishilist.model.WishListEntity;
import com.project.wishilist.model.dto.request.WishListRequestDto;
import com.project.wishilist.model.dto.response.ProductResponseDto;
import com.project.wishilist.model.dto.response.WishListResponseDto;
import com.project.wishilist.repository.ProductRepository;
import com.project.wishilist.repository.UserRepository;
import com.project.wishilist.repository.WishListRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WishListServiceTest {

    @InjectMocks
    WishListService wishListService;

    @Mock
    ProductRepository productRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    WishListRepository wishListRepository;

    @Mock
    private WishListEntity wishListEntityMock;

    @Mock
    ProductMapper productMapper;

    WishListRequestDto wishListRequestDto;
    WishListResponseDto wishListResponseDtoWithoutProducts;
    WishListResponseDto wishListResponseDtoWithProducts;
    WishListEntity wishListEntityWithoutProduct;
    WishListEntity wishListEntityWithOneProduct;
    ProductEntity productEntity1;
    ProductResponseDto productResponseDto1;
    ProductEntity productEntity2;
    List<ProductResponseDto> productResponseDtoList;
    UserEntity userEntity;

    @BeforeEach
    void setUp() {

        productEntity1 = new ProductEntity("1234567891234", "Produto Teste", "1234567891234");
        productEntity2 = new ProductEntity("1234567891231", "Produto Teste 2", "1234567891224");

        productResponseDto1 = new ProductResponseDto(productEntity1);
        productResponseDtoList = Arrays.asList(productResponseDto1);
        userEntity = new UserEntity("lucas@lucas.com", "lucas usuario", "1994-08-20", "Brazil", "38400067");

        wishListEntityWithoutProduct = new WishListEntity(userEntity, null);
        wishListEntityWithOneProduct = new WishListEntity(userEntity, Arrays.asList(productEntity1));

        wishListResponseDtoWithoutProducts = new WishListResponseDto(userEntity.getEmail(), userEntity.getUserCode(), null);
        wishListResponseDtoWithProducts = new WishListResponseDto(userEntity.getEmail(), userEntity.getUserCode(), productResponseDtoList);

        wishListRequestDto = new WishListRequestDto(productEntity1.getProductCode(), productEntity1.getEan(), userEntity.getEmail(), userEntity.getUserCode());
    }

    @Test
    void addProductWishListNoProductCodeNoEan() {

        WishListRequestDto wishListRequestDto1 = new WishListRequestDto(null, null, "lucas@lucas.com", "2ee93f6c-ce4d-44af-ad02-7094feb89f44");
        Assertions.assertThrows(BadRequestException.class, () -> {
            wishListService.addWishList(wishListRequestDto1);
        });
    }

    @Test
    void addProductWishListNoEmailNoUserCode() {

        WishListRequestDto wishListRequestDto1 = new WishListRequestDto("2ee93f6c-ce4d-44af-ad02-7094feb89f44", "1234567891234", null, null);
        Assertions.assertThrows(BadRequestException.class, () -> {
            wishListService.addWishList(wishListRequestDto1);
        });
    }

    @Test
    void addProductWishListProductNotFound() {

        when(productRepository.findByProductCodeOrEan(anyString(), anyString())).thenReturn(null);

        Assertions.assertThrows(NotFoundException.class, () -> {
            wishListService.addWishList(wishListRequestDto);
        });
    }

    @Test
    void addProductWishListUserNotFound() {

        when(productRepository.findByProductCodeOrEan(anyString(), anyString())).thenReturn(productEntity1);
        when(userRepository.findByEmailOrUserCode(anyString(), anyString())).thenReturn(null);

        Assertions.assertThrows(NotFoundException.class, () -> {
            wishListService.addWishList(wishListRequestDto);
        });
    }

    @Test
    void addProductWishListWishListMoreThan20ItensFail() {

        List<ProductEntity> mockedProductEntities = Collections.nCopies(20, new ProductEntity());

        when(productRepository.findByProductCodeOrEan(anyString(), anyString())).thenReturn(productEntity1);
        when(userRepository.findByEmailOrUserCode(anyString(), anyString())).thenReturn(userEntity);
        when(wishListRepository.findByUserEntity(any(UserEntity.class))).thenReturn(wishListEntityWithoutProduct);
        wishListEntityWithoutProduct.setProductEntities(mockedProductEntities);

        Assertions.assertThrows(Exception.class, () -> {
            wishListService.addWishList(wishListRequestDto);
        });
    }

    @Test
    void addProductWishListWishListEmptyWishListSucess() throws Exception {

        when(productRepository.findByProductCodeOrEan(anyString(), anyString())).thenReturn(productEntity1);
        when(userRepository.findByEmailOrUserCode(anyString(), anyString())).thenReturn(userEntity);
        when(wishListRepository.findByUserEntity(any(UserEntity.class))).thenReturn(null);
        when(wishListRepository.save(any(WishListEntity.class))).thenReturn(wishListEntityWithoutProduct);

        wishListService.addWishList(wishListRequestDto);

        verify(wishListRepository).save(any(WishListEntity.class));
    }

    @Test
    void addProductWishListWishListNotEmptyWishListSucess() throws Exception {

        MockitoAnnotations.openMocks(this);

        List<ProductEntity> productEntityList = new ArrayList<>();
        productEntityList.add(productEntity1);
        when(productRepository.findByProductCodeOrEan(anyString(), anyString())).thenReturn(productEntity1);
        when(userRepository.findByEmailOrUserCode(anyString(), anyString())).thenReturn(userEntity);
        when(wishListRepository.findByUserEntity(any(UserEntity.class))).thenReturn(wishListEntityWithOneProduct);

        wishListService.addWishList(wishListRequestDto);

        verify(wishListRepository).save(any(WishListEntity.class));
    }

    @Test
    void searchAllProductsWishListMissingUserParametersBadRequestException() {

        Assertions.assertThrows(BadRequestException.class, () -> {
            wishListService.searchAllProducts(null, null);
        });
    }

    @Test
    void searchAllProductsWishListUserNotFound() {

        when(userRepository.findByEmailOrUserCode(anyString(), any())).thenReturn(null);

        Assertions.assertThrows(NotFoundException.class, () -> {
            wishListService.searchAllProducts("teste@teste.com", null);
        });
    }

    @Test
    void searchAllProductsWishListWishListEmptySucess() throws Exception {

        when(userRepository.findByEmailOrUserCode(anyString(), any())).thenReturn(userEntity);
        when(wishListRepository.findByUserEntity(any(UserEntity.class))).thenReturn(null);

        WishListResponseDto wishListResponseDto = wishListService.searchAllProducts("teste@teste.com", null);

        assertEquals(wishListResponseDto, wishListResponseDtoWithoutProducts);
    }

    @Test
    void searchAllProductsWishListWishListNotEmptySucess() throws Exception {

        when(userRepository.findByEmailOrUserCode(anyString(), any())).thenReturn(userEntity);
        when(wishListRepository.findByUserEntity(any(UserEntity.class))).thenReturn(wishListEntityWithOneProduct);
        when(productMapper.toResponseDtolist(any(List.class))).thenReturn(productResponseDtoList);
        WishListResponseDto wishListResponseDto = wishListService.searchAllProducts("teste@teste.com", null);

        assertEquals(wishListResponseDto, wishListResponseDtoWithProducts);
    }

    @Test
    void searchIfProductInsideWishlistMissingUserParametersBadRequestException() {

        Assertions.assertThrows(BadRequestException.class, () -> {
            wishListService.searchProductWishList(null, null, null, "12345679101112");
        });
    }
    @Test
    void searchIfProductInsideWishlistMissingProductParametersBadRequestException() {

        Assertions.assertThrows(BadRequestException.class, () -> {
            wishListService.searchProductWishList("teste@teste.com", null, null, null);
        });
    }
    @Test
    void searchIfProductInsideWishlistUserNotFound() {
        when(userRepository.findByEmailOrUserCode(anyString(), any())).thenReturn(null);

        Assertions.assertThrows(NotFoundException.class, () -> {
            wishListService.searchProductWishList("teste@teste.com", null, null, "12345679101112");
        });
    }
    @Test
    void searchIfProductInsideWishlistProductNotFound() {
        when(userRepository.findByEmailOrUserCode(anyString(), any())).thenReturn(userEntity);
        when(productRepository.findByProductCodeOrEan(anyString(), any())).thenReturn(null);

        NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class, () -> {
            wishListService.searchProductWishList("teste@teste.com", null, "2ee93f6c-ce4d-44af-ad02-7094feb89f44", null);
        });
        assertEquals(notFoundException.getMessage(), "Produto não encontrado na base!");

    }
    @Test
    void searchIfProductInsideWishlistUserFoundButProductNotInsideWishlist() {
        when(userRepository.findByEmailOrUserCode(anyString(), any())).thenReturn(userEntity);
        when(productRepository.findByProductCodeOrEan(anyString(), any())).thenReturn(productEntity1);
        when(wishListRepository.findByUserEntity(any(UserEntity.class))).thenReturn(null);

        NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class, () -> {
            wishListService.searchProductWishList("teste@teste.com", null, "2ee93f6c-ce4d-44af-ad02-7094feb89f44", null);
        });

        assertEquals(notFoundException.getMessage(), "Produto não está na Wishlist do usuário");
    }
    @Test
    void searchIfProductInsideWishlistUserFoundAndProductInsideWishlist() throws BadRequestException, NotFoundException {
        when(userRepository.findByEmailOrUserCode(anyString(), any())).thenReturn(userEntity);
        when(productRepository.findByProductCodeOrEan(anyString(), any())).thenReturn(productEntity1);
        when(wishListRepository.findByUserEntity(any(UserEntity.class))).thenReturn(wishListEntityWithOneProduct);
        when(productMapper.toResponseDtolist(any(List.class))).thenReturn(productResponseDtoList);
        WishListResponseDto wishListResponseDtoTest = wishListService.searchProductWishList(userEntity.getEmail(), null, productEntity1.getProductCode(), null);

        assertEquals(wishListResponseDtoTest, wishListResponseDtoWithProducts);
    }

    @Test
    void deleteProductAtWishListButMissingUserParametersBadRequestExcpetion() {
        Assertions.assertThrows(BadRequestException.class, () -> {
            wishListService.deleteProductAtWishList(null, null, null, "12345679101112");
        });
    }
    @Test
    void deleteProductAtWishListButMissingProductParametersBadRequestExcpetion() {
        Assertions.assertThrows(BadRequestException.class, () -> {
            wishListService.deleteProductAtWishList("luccas@lucas.com", null, null, null);
        });
    }

    @Test
    void deleteProductAtWishListButUserNotFound() {
        when(userRepository.findByEmailOrUserCode(anyString(), any())).thenReturn(null);

        Assertions.assertThrows(NotFoundException.class, () -> {
            wishListService.deleteProductAtWishList("teste@teste.com", null, null, "12345679101112");
        });
    }
    @Test
    void deleteProductAtWishListButProductNotFound() {
        when(userRepository.findByEmailOrUserCode(anyString(), any())).thenReturn(userEntity);
        when(productRepository.findByProductCodeOrEan(any(), any())).thenReturn(null);

        Assertions.assertThrows(NotFoundException.class, () -> {
            wishListService.deleteProductAtWishList("teste@teste.com", null, null, "12345679101112");
        });
    }
    @Test
    void deleteProductAtWishListUserFoundProductFoundButNotInsideWishList() {
        when(userRepository.findByEmailOrUserCode(any(), any())).thenReturn(userEntity);
        when(productRepository.findByProductCodeOrEan(any(), any())).thenReturn(productEntity1);
        when(wishListRepository.findByUserEntity(any(UserEntity.class))).thenReturn(null);

        Assertions.assertThrows(NotFoundException.class, () -> {
            wishListService.deleteProductAtWishList(userEntity.getEmail(), null, null, productEntity1.getEan());
        });
    }
    @Test
    void deleteProductAtWishListUserFoundProductFoundButInsideWishList() throws BadRequestException, NotFoundException {
        when(userRepository.findByEmailOrUserCode(any(), any())).thenReturn(userEntity);
        when(productRepository.findByProductCodeOrEan(any(), any())).thenReturn(productEntity1);
        when(wishListRepository.findByUserEntity(any(UserEntity.class))).thenReturn(wishListEntityWithOneProduct);

        wishListService.deleteProductAtWishList(userEntity.getEmail(), null, null, productEntity1.getEan());

        verify(wishListRepository).save(any(WishListEntity.class));
    }
}
