package com.project.wishilist.repository;

import com.project.wishilist.model.UserEntity;
import com.project.wishilist.model.WishListEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends MongoRepository<WishListEntity, ObjectId> {

    WishListEntity findByUserEntity(UserEntity userEntity);
}
