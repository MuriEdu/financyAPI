package com.muriedu.financyapi.exceptions;

public class SeasonCreationException extends RuntimeException{

    public SeasonCreationException (String message){
        super("Season creation error: " + message);
    }
}
