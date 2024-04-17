package com.project.wishilist.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductRequestDto {
    @JsonProperty("name_product")
    @NotNull(message = "Preencher Nome do Produto!")
    @NotEmpty(message = "Nome do Produto não pode ser vazio!")
    public String nameProduct;

    @NotNull(message = "Preencher preço!")
    @NotEmpty(message = "preço não pode ser vazio!")
    @Pattern(regexp = "\\d+\\.\\d{1,2}", message = "O preço deve conter Somente numeros,ter até duas casas decimais e usar ponto para as casas decimais!")
    public String price;

    @NotNull(message = "Preencher EAN!")
    @Size(min = 13, max = 13, message = "EAN deve ter 13 digitos!")
    public String ean;
}
