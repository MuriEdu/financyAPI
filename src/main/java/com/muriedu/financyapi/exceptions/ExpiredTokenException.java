package com.muriedu.financyapi.exceptions;

public class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException(){
        super("JWT Token expired");
    }
}
