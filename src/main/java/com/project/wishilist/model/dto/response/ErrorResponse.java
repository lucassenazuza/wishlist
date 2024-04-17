package com.project.wishilist.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ErrorResponse<T> {
    @JsonProperty("error_message")
    private T object;

    public ErrorResponse(T object){
        this.object = object;
    }

}
