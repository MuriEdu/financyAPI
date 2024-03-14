package com.muriedu.financyapi.exceptions;

public class CashCreationException extends RuntimeException{
    public CashCreationException(String message){
        super("Cash creation error: " + message);
    }
}
