package com.opencbs.genesis.dto;

import com.opencbs.genesis.domain.enums.Gender;
import com.opencbs.genesis.dto.requests.FieldValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@ToString
@EqualsAndHashCode(callSuper = false)
@Data

public class OnlineApplyDto {
    private Gender gender;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String otherLanguages;
    private String address;
    private String postalCode;
    private String city;
    private String placeOfBirthCity;
    private String placeOfBirthCountry;
    private LocalDate birthDate;
    private List<FieldValue> fields;

}