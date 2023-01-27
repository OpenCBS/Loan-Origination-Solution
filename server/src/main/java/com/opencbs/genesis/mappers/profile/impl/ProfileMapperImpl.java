package com.opencbs.genesis.mappers.profile.impl;

import com.opencbs.genesis.annotations.Mapper;
import com.opencbs.genesis.domain.Profile;
import com.opencbs.genesis.dto.profile.ProfileDto;
import com.opencbs.genesis.dto.profile.field.ProfileFieldSectionDto;
import com.opencbs.genesis.mappers.profile.ProfileFieldSectionMapper;
import com.opencbs.genesis.mappers.profile.ProfileMapper;
import com.opencbs.genesis.services.ProfileFieldSectionService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Mapper
public class ProfileMapperImpl implements ProfileMapper {

    private final ProfileFieldSectionService profileFieldSectionService;
    private final ProfileFieldSectionMapper profileFieldSectionMapper;

    @Override
    public ProfileDto toProfileDto(Profile profile) {
        List<ProfileFieldSectionDto> profileFieldSectionDtos = profileFieldSectionService.findAll().stream()
                .map(it -> profileFieldSectionMapper.toProfileFieldSectionDto(profile, it))
                .collect(Collectors.toList());
        ProfileDto profileDto = new ProfileDto();
        profileDto.setCreatedDate(profile.getCreatedDate());
        profileDto.setId(profile.getId());
        profileDto.setProfileFieldSections(profileFieldSectionDtos);
        return profileDto;
    }
}
