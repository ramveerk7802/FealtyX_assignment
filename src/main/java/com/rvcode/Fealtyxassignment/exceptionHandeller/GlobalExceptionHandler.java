package com.rvcode.Fealtyxassignment.exceptionHandeller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentException.class)
    public ResponseEntity<?> studentNotFoundExceptionHandler(StudentException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MyCustomException.class)
    public ResponseEntity<?> dataException(MyCustomException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
