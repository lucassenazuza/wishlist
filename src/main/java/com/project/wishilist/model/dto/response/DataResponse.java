package com.project.wishilist.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class DataResponse<T> {
    @JsonProperty("data")
    private T object;

    public DataResponse(T object){
        this.object = object;
    }

}
