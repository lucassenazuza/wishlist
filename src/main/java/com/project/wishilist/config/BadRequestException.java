package com.project.wishilist.config;

public class BadRequestException extends Exception {
    public BadRequestException(String errorMessage){
        super(errorMessage);
    }
}
