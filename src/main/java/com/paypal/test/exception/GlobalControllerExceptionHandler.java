package com.paypal.test.exception;

import com.paypal.test.model.ApiErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;

/**
 * Created by seansong on 10/7/16.
 */
@RestControllerAdvice
public class GlobalControllerExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(value = { ConstraintViolationException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse constraintViolationException(ConstraintViolationException ex) {
        logger.error(ex.getMessage(), ex);
        return new ApiErrorResponse(400, 4001, ex);
    }

    @ExceptionHandler(value = { NoHandlerFoundException.class, NotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse noHandlerFoundException(Exception ex) {
        logger.error(ex.getMessage(), ex);
        return new ApiErrorResponse(404, 4041, ex);
    }

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse unknownException(Exception ex) {
        logger.error(ex.getMessage(), ex);
        return new ApiErrorResponse(500, 5002, ex);
    }
}
