package com.opencbs.genesis.exceptions;

/**
 * Created by Makhsut Islamov on 21.10.2016.
 */
public class InvalidCredentialsException extends UnauthorizedException{
    public InvalidCredentialsException() {
        super("invalid_credentials", "Invalid username or password.");
    }
}
