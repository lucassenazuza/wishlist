package com.project.wishilist.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserResponseDto {
    @JsonProperty("email")
    private String email;
    @JsonProperty("user_code")
    private String userCode;
    @JsonProperty("name_user")
    private String nameUser;
    @JsonProperty("date_birthday")
    private LocalDate dateBirthday;
    @JsonProperty("country")
    private String country;
    @JsonProperty("zip_code")
    private String zipcode;
    @JsonProperty("creation_time")
    private String creationTime;
}
