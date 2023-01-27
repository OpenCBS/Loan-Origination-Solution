package com.opencbs.genesis.dto.profile.field;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class ProfileFieldSectionCreateDto {
    @NotNull
    private String sectionCode;

    private List<ProfileFieldValueCreateDto> values;
}
