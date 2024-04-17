package com.project.wishilist.service;

import com.project.wishilist.mapper.UserMapper;
import com.project.wishilist.model.UserEntity;
import com.project.wishilist.model.dto.request.UserRequestDto;
import com.project.wishilist.model.dto.response.UserResponseDto;
import com.project.wishilist.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository UserRepository;
    @Mock
    UserMapper UserMapper;

    UserRequestDto userRequestDto;
    UserEntity userEntity;
    UserResponseDto userResponseDto;

    @BeforeEach
    void setUp() {
        userRequestDto = new UserRequestDto("lucas usuario", "lucas@lucas.com", "1994-08-20", "Brazil", "38400067");
        userEntity = new UserEntity("lucas@lucas.com", "lucas usuario", "1994-08-20", "Brazil", "38400067");
        userResponseDto = new UserResponseDto("lucas@lucas.com", userEntity.getUserCode(), userEntity.getNameUser(),
                userEntity.getDateBirthday(), userEntity.getCountry(), userEntity.getZipcode(), userEntity.getZipcode());
    }

    @Test
    void addUserSucess() {
        //Given When Then

        when(UserRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(UserMapper.toResponseDto(any(UserEntity.class))).thenReturn(userResponseDto);

        UserResponseDto UserResponseDtoTest = userService.addUser(userRequestDto);

        verify(UserRepository).save(any(UserEntity.class));
        assertEquals(UserResponseDtoTest, userResponseDto);
    }
}