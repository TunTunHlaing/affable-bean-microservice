package com.example.affablebeanui.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message){
        super(message);
    }
}
