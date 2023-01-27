package com.opencbs.genesis.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Created by Makhsut Islamov on 21.10.2016.
 */
public class ForbiddenException extends ApiException{
    public ForbiddenException(){
        this("Access denied.");
    }

    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, "forbidden", message);
    }
}
