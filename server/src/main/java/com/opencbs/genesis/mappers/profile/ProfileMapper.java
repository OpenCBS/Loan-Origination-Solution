package com.opencbs.genesis.mappers.profile;

import com.opencbs.genesis.domain.Profile;
import com.opencbs.genesis.dto.profile.ProfileDto;

public interface ProfileMapper {
    ProfileDto toProfileDto(Profile profile);
}
