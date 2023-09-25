package com.challenge.nisum.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.challenge.nisum.exceptions.NisumException;
import com.challenge.nisum.models.Mensaje;

@RestControllerAdvice
public class RestControllerHandlerExceptions {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Mensaje> handleException(Exception e){
        return ResponseEntity.internalServerError()
                .body(new Mensaje(e.getMessage()));
    }
    
    @ExceptionHandler(NisumException.class)
    public ResponseEntity<Object> responseBusinessException(NisumException e) {
        return new ResponseEntity(e.getMensaje(), e.getHttpCode());
    }
}
