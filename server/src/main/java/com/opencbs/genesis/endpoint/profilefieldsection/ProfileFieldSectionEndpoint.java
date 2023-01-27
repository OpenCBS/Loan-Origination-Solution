package com.opencbs.genesis.endpoint.profilefieldsection;

import com.opencbs.genesis.dto.profile.field.ProfileFieldSectionDto;

import java.util.List;

public interface ProfileFieldSectionEndpoint {
    List<ProfileFieldSectionDto> getAllProfileFieldSections();
}
