package com.opencbs.genesis.mappers.profile;

import com.opencbs.genesis.domain.Profile;
import com.opencbs.genesis.domain.ProfileField;
import com.opencbs.genesis.domain.ProfileFieldValue;
import com.opencbs.genesis.dto.profile.field.ProfileFieldSectionCreateDto;
import com.opencbs.genesis.dto.profile.field.ProfileFieldValueDto;
import com.opencbs.genesis.request.profilefieldrequest.ProfileFieldValueCreateRequest;
import com.opencbs.genesis.request.profilefieldrequest.ProfileFieldValueUpdateRequest;

import java.nio.file.Path;
import java.util.List;

public interface ProfileFieldValueMapper {
    ProfileFieldValueDto toProfileFieldValueDto(ProfileFieldValue profileFieldValue);

    List<ProfileFieldValueCreateRequest> toProfileFieldValueCreateRequest(Profile profile,
                                                                          ProfileFieldSectionCreateDto profileFieldSectionCreateDto);
    ProfileFieldValueCreateRequest getProfileFieldCreateRequestOne(Profile profile, ProfileField profileField, Path path);
}
