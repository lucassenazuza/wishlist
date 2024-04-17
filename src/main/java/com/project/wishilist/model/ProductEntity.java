package com.project.wishilist.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.wishilist.util.FormatDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "product_entity")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductEntity {
    @Id
    private ObjectId id;

    //EAN-13
    @Indexed(unique = true)
    @Size(min = 13, max = 13)
    private String ean;

    private String nameProduct;

    @Indexed(unique = true)
    private String productCode;

    private Double priceProduct;

    private String creationTime;

    public ProductEntity(String ean, String nameProduct, String priceProduct) {
        this.ean = ean;
        this.nameProduct = nameProduct;
        this.productCode = UUID.randomUUID().toString();
        this.priceProduct = Double.parseDouble(priceProduct);
        this.creationTime = FormatDateTime.formatDateTime(LocalDateTime.now());
    }
}
