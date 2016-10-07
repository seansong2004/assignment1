package com.paypal.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by seansong on 10/6/16.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such strings")
public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = -5160208463019956569L;

    public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
