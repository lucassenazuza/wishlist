package com.project.wishilist.service.interfaces;

import com.project.wishilist.model.dto.request.UserRequestDto;
import com.project.wishilist.model.dto.response.UserResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public interface IUserService {
    UserResponseDto addUser(UserRequestDto userRequestDto);
}
