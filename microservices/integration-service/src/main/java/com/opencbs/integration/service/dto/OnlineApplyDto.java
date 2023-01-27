package com.opencbs.integration.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class OnlineApplyDto {
    private String gender;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private List<FieldValue> fields;
}
