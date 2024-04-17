package com.project.wishilist.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.wishilist.model.dto.response.ProductResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "wishlist_entity")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class WishListEntity {
    @Id
    private ObjectId id;

    private UserEntity userEntity;

    private List<ProductEntity> productEntities;

    public WishListEntity(UserEntity userEntity, List<ProductEntity> productEntities){
        this.userEntity = userEntity;
        this.productEntities = productEntities;
    }
}