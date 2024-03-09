package com.muriedu.financyapi.DTOs;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class ApiErrors {

    private final List<String> errors;

    public ApiErrors(String message){
        this.errors = Arrays.asList(message);
    }
    public ApiErrors(List<String> message){
        this.errors = message;
    }
}