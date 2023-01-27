package com.opencbs.integration.service;

import com.opencbs.integration.service.dto.Client;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Component
public class ClientValidator {

    public void validate(Client client) {
        Assert.isTrue(!StringUtils.isEmpty(client.getGender()), "Gender is required.");
        Assert.isTrue(!StringUtils.isEmpty(client.getGender().trim()), "Gender is required.");
        Assert.isTrue(!StringUtils.isEmpty(client.getFirstName()), "First name is required.");
        Assert.isTrue(!StringUtils.isEmpty(client.getFirstName().trim()), "First name is required.");
        Assert.isTrue(!StringUtils.isEmpty(client.getLastName()), "Last name is required.");
        Assert.isTrue(!StringUtils.isEmpty(client.getLastName().trim()), "Last name is required.");
        Assert.isTrue(!StringUtils.isEmpty(client.getPhoneNumber()), "Phone number is required.");
        Assert.isTrue(!StringUtils.isEmpty(client.getPhoneNumber().trim()), "Phone number is required.");
        Assert.isTrue(!StringUtils.isEmpty(client.getEmail()), "Email is required.");
        Assert.isTrue(!StringUtils.isEmpty(client.getEmail().trim()), "Email is required.");
        Assert.isTrue(!StringUtils.isEmpty(client.getLanguage()), "Main language is required.");
        Assert.isTrue(!StringUtils.isEmpty(client.getLanguage().trim()), "Main language is required.");
        Assert.isTrue(!StringUtils.isEmpty(client.getDateOfBirth()), "Date of birth is required.");
        Assert.isTrue(!StringUtils.isEmpty(client.getDateOfBirth().trim()), "Date of birth is required.");
        Assert.isTrue(!StringUtils.isEmpty(client.getPlaceOfBirth()), "Place of birth is required.");
        Assert.isTrue(!StringUtils.isEmpty(client.getPlaceOfBirth().trim()), "Place of birth is required.");
        Assert.isTrue(!StringUtils.isEmpty(client.getCountry()), "Country is required.");
        Assert.isTrue(!StringUtils.isEmpty(client.getCountry().trim()), "Country is required.");
    }
}
