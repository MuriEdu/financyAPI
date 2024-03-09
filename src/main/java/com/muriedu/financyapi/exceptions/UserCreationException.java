package com.muriedu.financyapi.exceptions;

public class UserCreationException extends RuntimeException{
    public UserCreationException(String message){
        super("User Creation Error: " + message);
    }
}
