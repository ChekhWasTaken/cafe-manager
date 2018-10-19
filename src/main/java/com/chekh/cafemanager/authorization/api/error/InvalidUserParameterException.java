package com.chekh.cafemanager.authorization.api.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidUserParameterException extends RuntimeException {
    public InvalidUserParameterException(String message) {
        super(message);
    }
}
