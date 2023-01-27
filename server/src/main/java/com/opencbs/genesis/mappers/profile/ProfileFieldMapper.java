package com.opencbs.genesis.mappers.profile;

import com.opencbs.genesis.domain.Profile;
import com.opencbs.genesis.domain.ProfileField;
import com.opencbs.genesis.dto.profile.field.ProfileFieldDto;

public interface ProfileFieldMapper {
    ProfileFieldDto toProfileFieldDto(ProfileField profileField, Profile profile);

    ProfileFieldDto toProfileFieldDto(ProfileField profileField);
}
