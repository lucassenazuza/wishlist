package com.project.wishilist.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WishListResponseDto {
    @JsonProperty("email")
    private String email;

    @JsonProperty("user_code")
    private String userCode;

    @JsonProperty("products")
    private List<ProductResponseDto> products;
}
