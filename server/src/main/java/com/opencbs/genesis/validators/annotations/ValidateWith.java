package com.opencbs.genesis.validators.annotations;

import com.opencbs.genesis.validators.Validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Makhsut Islamov on 07.11.2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ValidateWith {
    Class<? extends Validator> value();
}
