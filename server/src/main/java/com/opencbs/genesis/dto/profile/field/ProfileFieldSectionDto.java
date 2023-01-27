package com.opencbs.genesis.dto.profile.field;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProfileFieldSectionDto {
    private long id;
    private String caption;
    private int order;
    private String code;
    private List<ProfileFieldDto> profileFields;
}
