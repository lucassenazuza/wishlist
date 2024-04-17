package com.project.wishilist.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserRequestDto {

    @NotNull(message = "Preencher Nome do usuário!")
    @NotEmpty(message = "Nome do usuário não pode ser vazio!")
    @JsonProperty("name_user")
    public String nameUser;

    @NotNull(message = "Preencher email do usuário!")
    @NotEmpty(message = "email não pode estar vazio!")
    @JsonProperty("email")
    public String email;

    @JsonProperty("date_birthday")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-mm-dd")
    public String dateBirthday;

    @JsonProperty("country")
    public String country;

    @JsonProperty("zip_code")
    public String zipCode;

}

