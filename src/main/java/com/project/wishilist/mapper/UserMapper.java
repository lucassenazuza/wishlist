package com.project.wishilist.mapper;


import com.project.wishilist.model.UserEntity;
import com.project.wishilist.model.dto.response.UserResponseDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toResponseDto(UserEntity userEntity);
}
