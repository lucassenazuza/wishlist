package com.project.wishilist.config;

public class NotFoundException extends Exception {
    public NotFoundException(String errorMessage){
        super(errorMessage);
    }
}
