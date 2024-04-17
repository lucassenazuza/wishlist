package com.project.wishilist.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WishListRequestDto {

    @JsonProperty("product_code")
    private String productCode;
    @JsonProperty("ean")
    private String ean;
    @JsonProperty("email")
    public String email;
    @JsonProperty("user_code")
    public String userCode;


}

