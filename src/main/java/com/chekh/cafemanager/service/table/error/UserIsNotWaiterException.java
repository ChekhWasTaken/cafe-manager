package com.chekh.cafemanager.service.table.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class UserIsNotWaiterException extends RuntimeException {
    public UserIsNotWaiterException(String message) {
        super(message);
    }
}
