package com.opencbs.genesis.endpoint.profilefieldsection.impl;

import com.opencbs.genesis.dto.profile.field.ProfileFieldSectionDto;
import com.opencbs.genesis.endpoint.profilefieldsection.ProfileFieldSectionEndpoint;
import com.opencbs.genesis.mappers.profile.ProfileFieldSectionMapper;
import com.opencbs.genesis.services.ProfileFieldSectionService;
import com.opencbs.genesis.services.ProfileFieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileFieldSectionEndpointImpl implements ProfileFieldSectionEndpoint {

    private final ProfileFieldSectionService profileFieldSectionService;
    private final ProfileFieldSectionMapper profileFieldSectionMapper;
    private final ProfileFieldService profileFieldService;

    @Override
    public List<ProfileFieldSectionDto> getAllProfileFieldSections() {
        return profileFieldSectionService.findAll().stream()
                .map(it -> profileFieldSectionMapper.toProfileFieldSectionDto(it, profileFieldService.getAllBySection(it)))
                .collect(Collectors.toList());
    }
}
