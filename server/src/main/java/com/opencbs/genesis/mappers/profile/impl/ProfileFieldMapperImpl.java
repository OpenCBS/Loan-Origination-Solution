package com.opencbs.genesis.mappers.profile.impl;

import com.opencbs.genesis.annotations.Mapper;
import com.opencbs.genesis.domain.Profile;
import com.opencbs.genesis.domain.ProfileField;
import com.opencbs.genesis.dto.profile.field.ProfileFieldDto;
import com.opencbs.genesis.dto.profile.field.ProfileFieldValueDto;
import com.opencbs.genesis.mappers.profile.ProfileFieldMapper;
import com.opencbs.genesis.mappers.profile.ProfileFieldValueMapper;
import com.opencbs.genesis.services.ProfileFieldValueService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
@RequiredArgsConstructor
public class ProfileFieldMapperImpl implements ProfileFieldMapper {

    private final ProfileFieldValueMapper profileFieldValueMapper;
    private final ProfileFieldValueService profileFieldValueService;

    public ProfileFieldDto toProfileFieldDto(ProfileField profileField, Profile profile) {
        List<ProfileFieldValueDto> profileFieldValueDtos = profileFieldValueService.findByProfileAndField(profile, profileField)
                .stream()
                .map(profileFieldValueMapper::toProfileFieldValueDto)
                .collect(Collectors.toList());
        ProfileFieldDto profileFieldDto = getProfileFieldDto(profileField);
        profileFieldDto.setProfileFieldValues(profileFieldValueDtos);
        return profileFieldDto;
    }

    @Override
    public ProfileFieldDto toProfileFieldDto(ProfileField profileField) {
        return getProfileFieldDto(profileField);
    }

    private ProfileFieldDto getProfileFieldDto(ProfileField profileField) {
        ProfileFieldDto profileFieldDto = new ProfileFieldDto();
        profileFieldDto.setCaption(profileField.getCaption());
        profileFieldDto.setExtra(profileField.getExtra());
        profileFieldDto.setFieldType(profileField.getFieldType());
        profileFieldDto.setMandatory(profileField.isMandatory());
        profileFieldDto.setOrder(profileField.getOrder());
        profileFieldDto.setUnique(profileField.isUnique());
        profileFieldDto.setCode(profileField.getCode());
        profileFieldDto.setSectionCode(profileField.getSectionCode());
        profileFieldDto.setSectionId(profileField.getSection().getId());
        return profileFieldDto;
    }
}
