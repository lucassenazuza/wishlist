package com.project.wishilist.controller;


import com.project.wishilist.model.dto.request.UserRequestDto;
import com.project.wishilist.model.dto.response.DataResponse;
import com.project.wishilist.model.dto.response.UserResponseDto;
import com.project.wishilist.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    IUserService userSerivce;

    @PostMapping
    public ResponseEntity<DataResponse<UserResponseDto>> addUser(@RequestBody @Valid UserRequestDto userRequestDto){

        UserResponseDto userResponseDto = userSerivce.addUser(userRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new DataResponse<UserResponseDto>(userResponseDto));
    }
}
