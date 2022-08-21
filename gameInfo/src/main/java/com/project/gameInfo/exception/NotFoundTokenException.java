package com.project.gameInfo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.annotation.HttpMethodConstraint;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NotFoundTokenException extends RuntimeException{

    public NotFoundTokenException(String message) {
        super(message);
    }
}
