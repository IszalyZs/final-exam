package com.company.trexshelter.config;

import com.company.trexshelter.exception.DogException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyControllerAdvice {
private Logger log= LoggerFactory.getLogger(MyControllerAdvice.class);

    @ExceptionHandler(DogException.class)
    public ResponseEntity<String> dogExceptionHandler(DogException dogException){
        log.error(dogException.getMessage());
        return new ResponseEntity<>(dogException.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
