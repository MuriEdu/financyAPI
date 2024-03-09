package com.muriedu.financyapi.API;

import com.muriedu.financyapi.DTOs.ApiErrors;
import com.muriedu.financyapi.exceptions.DataNotFoundedException;
import com.muriedu.financyapi.exceptions.ExpiredTokenException;
import com.muriedu.financyapi.exceptions.InvalidCredentialException;
import com.muriedu.financyapi.exceptions.UserCreationException;
import static org.springframework.http.HttpStatus.*;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(UserCreationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleUserCreationException(UserCreationException ex){
        String message = ex.getMessage();
        return new ApiErrors(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleMthodNotValidException(MethodArgumentNotValidException ex){
        List<String> messages = ex.getBindingResult()
                .getAllErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return new ApiErrors(messages);
    }

    @ExceptionHandler(DataNotFoundedException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleDataNotFoundedException(DataNotFoundedException ex){
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(InvalidCredentialException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ApiErrors handleInvalidCedentialException(InvalidCredentialException ex){
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(ExpiredTokenException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ApiErrors handleExpiredTokenException(ExpiredTokenException ex){
        return new ApiErrors(ex.getMessage());
    }



}


