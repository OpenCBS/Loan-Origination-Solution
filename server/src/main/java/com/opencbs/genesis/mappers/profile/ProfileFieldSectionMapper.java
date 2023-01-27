package com.opencbs.genesis.mappers.profile;

import com.opencbs.genesis.domain.Profile;
import com.opencbs.genesis.domain.ProfileField;
import com.opencbs.genesis.domain.ProfileFieldSection;
import com.opencbs.genesis.dto.profile.field.ProfileFieldSectionDto;

import java.util.List;

public interface ProfileFieldSectionMapper {
    ProfileFieldSectionDto toProfileFieldSectionDto(Profile profile, ProfileFieldSection section);

    ProfileFieldSectionDto toProfileFieldSectionDto(ProfileFieldSection profileFieldSection,
                                                    List<ProfileField> profileFields);
}
