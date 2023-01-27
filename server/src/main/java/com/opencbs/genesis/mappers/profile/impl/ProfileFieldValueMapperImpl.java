package com.opencbs.genesis.mappers.profile.impl;

import com.opencbs.genesis.annotations.Mapper;
import com.opencbs.genesis.domain.Profile;
import com.opencbs.genesis.domain.ProfileField;
import com.opencbs.genesis.domain.ProfileFieldValue;
import com.opencbs.genesis.domain.enums.FieldType;
import com.opencbs.genesis.dto.profile.field.ProfileFieldSectionCreateDto;
import com.opencbs.genesis.dto.profile.field.ProfileFieldValueCreateDto;
import com.opencbs.genesis.dto.profile.field.ProfileFieldValueDto;
import com.opencbs.genesis.mappers.profile.ProfileFieldValueMapper;
import com.opencbs.genesis.request.profilefieldrequest.ProfileFieldValueCreateRequest;
import com.opencbs.genesis.services.LookupValueExtractService;
import com.opencbs.genesis.services.ProfileFieldService;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityNotFoundException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
@RequiredArgsConstructor
public class ProfileFieldValueMapperImpl implements ProfileFieldValueMapper {

    private final ProfileFieldService profileFieldService;
    private final LookupValueExtractService lookupValueExtractService;

    @Override
    public ProfileFieldValueDto toProfileFieldValueDto(ProfileFieldValue profileFieldValue) {
        ProfileFieldValueDto profileFieldValueDto = new ProfileFieldValueDto();
        profileFieldValueDto.setSectionCode(profileFieldValue.getSectionCode());
        profileFieldValueDto.setCode(profileFieldValue.getField().getCode());
        profileFieldValueDto.setId(profileFieldValue.getId());

        if (profileFieldValue.getField().getFieldType() == FieldType.LOOKUP) {
            String key = (String) profileFieldValue.getField().getExtra().get("key");
            String value = lookupValueExtractService.getValue(key, profileFieldValue.getValue());
            profileFieldValueDto.setValue(value);
            return profileFieldValueDto;
        }

        profileFieldValueDto.setValue(profileFieldValue.getValue());
        return profileFieldValueDto;
    }

    @Override
    public List<ProfileFieldValueCreateRequest> toProfileFieldValueCreateRequest(Profile profile, ProfileFieldSectionCreateDto profileFieldSectionCreateDto) {
        return profileFieldSectionCreateDto.getValues().stream()
                .filter(it -> it != null && it.getValues() != null)
                .map(it -> it.getValues().stream()
                        .map(customFieldValue ->
                                getProfileFieldCreateRequest(profile, profileFieldSectionCreateDto, it, customFieldValue))
                        .collect(Collectors.toList()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private ProfileFieldValueCreateRequest getProfileFieldCreateRequest(Profile profile, ProfileFieldSectionCreateDto profileFieldSectionCreateDto, ProfileFieldValueCreateDto it, String customFieldValue) {
        ProfileField profileField = profileFieldService.findByCodeAndSectionCode(it.getCode(), profileFieldSectionCreateDto.getSectionCode())
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format(
                                "There is no Profile Field Section with code %s and section code %s", it.getCode(), profileFieldSectionCreateDto.getSectionCode())));
        return new ProfileFieldValueCreateRequest(
                profileFieldSectionCreateDto.getSectionCode(), profile, profileField, customFieldValue
        );
    }

    public ProfileFieldValueCreateRequest getProfileFieldCreateRequestOne(Profile profile, ProfileField profileField, Path path) {
        return new ProfileFieldValueCreateRequest(profileField.getSectionCode(), profile, profileField, path.toString());
    }
}
