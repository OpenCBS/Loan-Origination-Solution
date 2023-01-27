package com.opencbs.integration.service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Client {
    private String gender;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;
    private String postalCode;
    private String city;
    private String country;
    private String language;
    private String dateOfBirth;
    private String placeOfBirth;
    private String citizenship;
    private String education;
    private String professionalSituation;
    private String familyStatus;
    private String hearAbout;
    private String projectDescription;
    private String requestedAmount;
    private String purposeOfCredit;
    private String previousCredit;
    private BigDecimal repayEachMonth;
    private Boolean companyCreated;
    private String companyName;
    private String companyStatus;
    private String companyCreationDate;
    private Integer numberOfEmployees;
}
