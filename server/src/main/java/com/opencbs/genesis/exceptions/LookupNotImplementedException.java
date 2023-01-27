package com.opencbs.genesis.exceptions;

public class LookupNotImplementedException extends RuntimeException {

    public LookupNotImplementedException(String name) {
        super(String.format("%s is not implemented as lookup", name));
    }
}
