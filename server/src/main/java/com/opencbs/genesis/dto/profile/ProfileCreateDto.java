package com.opencbs.genesis.dto.profile;

import com.opencbs.genesis.dto.profile.field.ProfileFieldSectionCreateDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class ProfileCreateDto {
    @NotNull
    @NotEmpty
    @Valid
    private List<ProfileFieldSectionCreateDto> profileSections;
}
