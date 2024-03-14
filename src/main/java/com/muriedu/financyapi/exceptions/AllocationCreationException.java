package com.muriedu.financyapi.exceptions;

public class AllocationCreationException extends RuntimeException{
    public AllocationCreationException(String message){
        super("Allocation creation error: " + message);
    }
}
