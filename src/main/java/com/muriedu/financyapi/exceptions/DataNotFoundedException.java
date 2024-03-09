package com.muriedu.financyapi.exceptions;

public class DataNotFoundedException extends RuntimeException{
    public DataNotFoundedException(){
        super("Data not founded");
    }

    public DataNotFoundedException(String message){
        super(message);
    }
}
