package com.example.apisecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class PasswordnotMatchError extends ResponseStatusException {


    public PasswordnotMatchError() {
        super(HttpStatus.BAD_REQUEST,"Password Not match!!");
    }
}
