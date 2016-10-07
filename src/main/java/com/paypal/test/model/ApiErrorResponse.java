package com.paypal.test.model;

import com.paypal.test.util.ObjectMappers;

/**
 * Created by seansong on 10/7/16.
 */
public class ApiErrorResponse {
    private int status;
    private int code;
    private String message;

    public ApiErrorResponse() {}

    public ApiErrorResponse(int status, int code, Exception ex) {
        this.status = status;
        this.code = code;
        this.message = ex.getMessage();
    }

    public int getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return ObjectMappers.toString(this);
    }
}
