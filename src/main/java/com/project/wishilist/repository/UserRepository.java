package com.project.wishilist.repository;

import com.project.wishilist.model.ProductEntity;
import com.project.wishilist.model.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {

    @Query("{$or:["
            + "{$and:[{ 'email': {$exists: true} }, { 'email': ?0 }]},"
            + "{$and:[{ 'userCode': {$exists: true} }, { 'userCode': ?1 }]}"
            + "]}")
    UserEntity findByEmailOrUserCode(String email, String user_code);
}
