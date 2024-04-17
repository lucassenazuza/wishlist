package com.project.wishilist.service;

import com.project.wishilist.mapper.UserMapper;
import com.project.wishilist.model.ProductEntity;
import com.project.wishilist.model.UserEntity;
import com.project.wishilist.model.dto.request.UserRequestDto;
import com.project.wishilist.model.dto.response.UserResponseDto;
import com.project.wishilist.repository.UserRepository;
import com.project.wishilist.service.interfaces.IUserService;
import com.project.wishilist.util.FormatDateTime;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;


    public UserResponseDto addUser(UserRequestDto userRequestDto) {

        String nameUser = userRequestDto.getNameUser();
        String email = userRequestDto.getEmail();

        logger.info("Cadastrando Usuário - nome: " + nameUser + ", email: " + email);

        UserEntity userEntity = new UserEntity(userRequestDto.getEmail(), userRequestDto.getNameUser(),
                userRequestDto.getDateBirthday(), userRequestDto.getCountry(),
                userRequestDto.getZipCode());

        userRepository.save(userEntity);

        return userMapper.toResponseDto(userEntity);
    }
}
