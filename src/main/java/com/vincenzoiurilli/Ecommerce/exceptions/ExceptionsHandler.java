package com.vincenzoiurilli.Ecommerce.exceptions;

import com.vincenzoiurilli.Ecommerce.dto.errors.ErrorDTO;
import com.vincenzoiurilli.Ecommerce.dto.errors.ErrorWithListDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    public ErrorDTO handleUnauthorized(UnauthorizedException ex) {
        return new ErrorDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ErrorWithListDTO handleBadRequest(ValidationException ex) {
        return new ErrorWithListDTO(ex.getMessage(), LocalDateTime.now(), ex.getErrorsList());
    }

}
