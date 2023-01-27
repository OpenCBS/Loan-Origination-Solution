package com.opencbs.genesis.dto.profile.field;

import com.opencbs.genesis.domain.ProfileFieldExtra;
import com.opencbs.genesis.domain.enums.FieldType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProfileFieldDto {
    private String caption;
    private ProfileFieldExtra extra;
    private FieldType fieldType;
    private boolean mandatory;
    private int order;
    private boolean unique;
    private String code;
    private String sectionCode;
    private long sectionId;
    private List<ProfileFieldValueDto> profileFieldValues;
}
