package com.project.wishilist.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.wishilist.util.FormatDateTime;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "user_entity")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserEntity {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true)
    private String userCode;

    private String nameUser;

    private LocalDate dateBirthday;

    private String country;

    private String zipcode;

    private String creationTime;

    public UserEntity(String email, String nameUser, String dateBirthday, String country, String zipcode) {
        this.email = email;
        this.userCode = UUID.randomUUID().toString();
        this.nameUser = nameUser;
        this.dateBirthday = LocalDate.parse(dateBirthday);
        this.country = country;
        this.zipcode = zipcode;
        this.creationTime = FormatDateTime.formatDateTime(LocalDateTime.now());
    }

}
