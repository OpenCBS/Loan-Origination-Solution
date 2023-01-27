package com.opencbs.genesis.dto.profile.field;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class ProfileFieldValueCreateDto {
    @NotNull
    private String code;

    @NotNull
    private List<String> values;
}
