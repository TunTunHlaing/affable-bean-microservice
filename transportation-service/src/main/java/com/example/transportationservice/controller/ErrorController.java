package com.example.transportationservice.controller;

import com.example.transportationservice.error.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@ControllerAdvice
public class ErrorController {

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity handleException(Throwable throwable)throws  Throwable{

           return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Login Error!");

    }
}
