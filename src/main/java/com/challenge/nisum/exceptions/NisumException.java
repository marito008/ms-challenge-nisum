package com.challenge.nisum.exceptions;

import org.springframework.http.HttpStatus;

import com.challenge.nisum.models.Mensaje;

import lombok.Data;

@Data
public class NisumException extends Exception {
    private final HttpStatus httpCode;
    private final Mensaje mensaje;

    public NisumException(HttpStatus httpStatus, Mensaje mensaje) {
        this.httpCode = httpStatus;
        this.mensaje = mensaje;
    }

}
