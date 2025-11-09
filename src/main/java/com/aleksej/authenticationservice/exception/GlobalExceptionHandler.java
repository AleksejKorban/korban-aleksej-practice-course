package com.aleksej.authenticationservice.exception;



import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({RuntimeException.class, JwtException.class, ExpiredJwtException.class, MalformedJwtException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleSecurityExceptions(Exception ex) {

        System.err.println("Security Exception: " + ex.getMessage());

        return Map.of("error", "Unauthorized", "message", ex.getMessage());
    }
}