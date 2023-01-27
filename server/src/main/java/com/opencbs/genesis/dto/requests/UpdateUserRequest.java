package com.opencbs.genesis.dto.requests;

import lombok.Data;

/**
 * Created by alopatin on 25-Apr-17.
 */
@Data
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String mobilePhone;
    private String phoneNumber;
    private String currentOccupation;
    private String spokenLanguages;
    private String specialization;
    private String availability;
    private String alreadyVolunteered;
    private String supportOther;
    private String supportPromotersOther;
    private String citizenship;
    private String support;
    private String supportPromoters;
    private String address;
    private String preferredWorkingLanguages;
    private String street;
    private String postalCode;
}
