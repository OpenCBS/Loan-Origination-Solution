package com.opencbs.genesis.exceptions;

import org.springframework.http.HttpStatus;

import javax.servlet.ServletException;

/**
 * Created by Makhsut Islamov on 21.10.2016.
 */
public class ApiException extends ServletException{
    private HttpStatus httpStatus;
    private String errorCode;

    public ApiException(HttpStatus httpStatus, String errorCode, String message){
        super(message);

        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
