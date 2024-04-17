package com.project.wishilist.repository;

import com.project.wishilist.model.ProductEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, ObjectId> {

    @Query("{$or:["
            + "{$and:[{ 'productCode': {$exists: true} }, { 'productCode': ?0 }]},"
            + "{$and:[{ 'ean': {$exists: true} }, { 'ean': ?1 }]}"
            + "]}")
    ProductEntity findByProductCodeOrEan(String productCode, String ean);
}
