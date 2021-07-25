package com.company.trexshelter.config;

import com.company.trexshelter.exception.BreedException;
import com.company.trexshelter.exception.DogException;
import com.company.trexshelter.exception.RanchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyControllerAdvice {
    private Logger log = LoggerFactory.getLogger(MyControllerAdvice.class);

    @ExceptionHandler(DogException.class)
    public ResponseEntity<String> dogExceptionHandler(DogException dogException) {
        log.error(dogException.getMessage());
        return new ResponseEntity<>(dogException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleOtherErrors(HttpMessageNotReadableException formatException) {
        String error = formatException.getRootCause().getMessage();
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BreedException.class)
    public ResponseEntity<String> breedExceptionHandler(BreedException breedException) {
        log.error(breedException.getMessage());
        return new ResponseEntity<>(breedException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RanchException.class)
    public ResponseEntity<String> ranchExceptionHandler(RanchException ranchException) {
        log.error(ranchException.getMessage());
        return new ResponseEntity<>(ranchException.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
