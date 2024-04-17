package com.project.wishilist.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.wishilist.model.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductResponseDto {
    @JsonProperty("name")
    public String name;
    @JsonProperty("price")
    public Double price;
    @JsonProperty("ean")
    public String ean;
    @JsonProperty("product_code")
    private String productCode;
    @JsonProperty("creation_time")
    public String creationTime;

    public ProductResponseDto(ProductEntity productEntity){
        this.name = productEntity.getNameProduct();
        this.price = productEntity.getPriceProduct();
        this.ean = productEntity.getEan();
        this.productCode = productEntity.getProductCode();
        this.creationTime = productEntity.getCreationTime();
    }
}
