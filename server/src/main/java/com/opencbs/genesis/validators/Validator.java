package com.opencbs.genesis.validators;

import com.opencbs.genesis.exceptions.ApiException;

/**
 * Created by Makhsut Islamov on 25.10.2016.
 */
public interface Validator {
    void validate(Object ... params) throws ApiException;
}
