package com.opencbs.genesis.validators.helpers;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * Created by Makhsut Islamov on 04.11.2016.
 */
public class EmailValidator {
    private final static String pattern = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    public static boolean isValid(String email) {
        return !StringUtils.isEmpty(email) && Pattern.matches(pattern, email);
    }

    public static void validate(String email){
        Assert.isTrue(isValid(email), "email is not valid.");
    }
}
