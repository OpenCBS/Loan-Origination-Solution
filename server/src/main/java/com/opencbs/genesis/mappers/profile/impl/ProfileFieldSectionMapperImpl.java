package com.opencbs.genesis.mappers.profile.impl;

import com.opencbs.genesis.annotations.Mapper;
import com.opencbs.genesis.domain.Profile;
import com.opencbs.genesis.domain.ProfileField;
import com.opencbs.genesis.domain.ProfileFieldSection;
import com.opencbs.genesis.dto.profile.field.ProfileFieldDto;
import com.opencbs.genesis.dto.profile.field.ProfileFieldSectionDto;
import com.opencbs.genesis.mappers.profile.ProfileFieldMapper;
import com.opencbs.genesis.mappers.profile.ProfileFieldSectionMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
@RequiredArgsConstructor
public class ProfileFieldSectionMapperImpl implements ProfileFieldSectionMapper {

    private final ProfileFieldMapper profileFieldMapper;

    @Override
    public ProfileFieldSectionDto toProfileFieldSectionDto(Profile profile, ProfileFieldSection profileFieldSection) {
        List<ProfileFieldDto> profileFieldDtos = profileFieldSection.getProfileFields()
                .stream()
                .map(it -> profileFieldMapper.toProfileFieldDto(it, profile))
                .collect(Collectors.toList());
        return getProfileFieldSectionDto(profileFieldSection, profileFieldDtos);
    }

    private ProfileFieldSectionDto getProfileFieldSectionDto(ProfileFieldSection profileFieldSection, List<ProfileFieldDto> profileFieldDtos) {
        ProfileFieldSectionDto profileFieldSectionDto = new ProfileFieldSectionDto();
        profileFieldSectionDto.setId(profileFieldSection.getId());
        profileFieldSectionDto.setCaption(profileFieldSection.getCaption());
        profileFieldSectionDto.setOrder(profileFieldSection.getOrder());
        profileFieldSectionDto.setCode(profileFieldSection.getCode());
        profileFieldSectionDto.setProfileFields(profileFieldDtos);
        return profileFieldSectionDto;
    }

    @Override
    public ProfileFieldSectionDto toProfileFieldSectionDto(ProfileFieldSection profileFieldSection, List<ProfileField> profileFields) {
        List<ProfileFieldDto> profileFieldDtos = profileFieldSection.getProfileFields()
                .stream()
                .map(profileFieldMapper::toProfileFieldDto)
                .collect(Collectors.toList());
        return getProfileFieldSectionDto(profileFieldSection, profileFieldDtos);
    }
}
