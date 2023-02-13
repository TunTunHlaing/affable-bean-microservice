package com.example.apisecurity.controller;

import com.example.apisecurity.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@RestController
@ControllerAdvice
public class ErrorController {

    @ExceptionHandler({PasswordnotMatchError.class, InvalidCredentialError.class, NoBearerToken.class, UserPrincipalNotFoundException.class,
    UnAuthenticatedError.class, InvalidLinkError.class})
    public ResponseEntity handleException(Throwable throwable)throws Throwable{

        if (throwable instanceof PasswordnotMatchError) {
            return ResponseEntity
                    .badRequest()
                    .body("Password did not match!");
        }
        else if (throwable instanceof InvalidCredentialError) {
            return ResponseEntity
                    .status(401)
                    .body("Invalid Credentials.");
        }
        else if (throwable instanceof NoBearerToken) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("No Bearer token!!!");
        }
        else if (throwable instanceof UserNotFoundException) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User Not Found From token!!!");
        }
        else if (throwable instanceof UnAuthenticatedError){
            return ResponseEntity
                    .status(401)
                    .body("Refresh Token is Not Authenticated");
        }

        return null;
    }
}
