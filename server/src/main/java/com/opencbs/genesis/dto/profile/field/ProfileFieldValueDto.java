package com.opencbs.genesis.dto.profile.field;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileFieldValueDto {
    private Long id;
    private String sectionCode;
    private String code;
    private String value;
}
