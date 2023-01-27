package com.opencbs.genesis.exceptions;


/**
 * Created by Makhsut Islamov on 21.10.2016.
 */
public class SessionExpiredException extends UnauthorizedException{
    public SessionExpiredException() {
        super("session_expired","Session expired.");
    }
}
