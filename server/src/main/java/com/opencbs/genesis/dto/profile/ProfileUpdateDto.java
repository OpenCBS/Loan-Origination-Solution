package com.opencbs.genesis.dto.profile;

import com.opencbs.genesis.dto.profile.field.ProfileFieldSectionCreateDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProfileUpdateDto {
    private List<ProfileFieldSectionCreateDto> profileSections;
}
