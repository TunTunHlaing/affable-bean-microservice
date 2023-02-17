package com.example.transportationservice.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AuthorizationException extends ResponseStatusException {

    public AuthorizationException(){
        super(HttpStatus.FORBIDDEN,"Authorization Error!!!");
    }
}
