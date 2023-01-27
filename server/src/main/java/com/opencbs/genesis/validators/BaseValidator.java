package com.opencbs.genesis.validators;

import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.exceptions.ValidationException;
import org.springframework.util.Assert;

/**
 * Created by Makhsut Islamov on 25.10.2016.
 */
abstract class BaseValidator implements Validator{
    @Override
    public void validate(Object... params) throws ApiException {
        Assert.notNull(params, "Params in validation can not be null.");
        validateInternal(params);
    }

    protected abstract void validateInternal(Object... params) throws ApiException;
}
