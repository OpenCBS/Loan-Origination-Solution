package com.opencbs.genesis.controllers;

import com.opencbs.genesis.dto.profile.field.ProfileFieldSectionDto;
import com.opencbs.genesis.endpoint.profilefieldsection.ProfileFieldSectionEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RequiredArgsConstructor
@RestControllerAdvice
@RequestMapping("/api/profiles/fields")
public class ProfileFieldsController {

    private final ProfileFieldSectionEndpoint profileFieldSectionEndpoint;

    @GetMapping()
    public List<ProfileFieldSectionDto> getProfileFields() {
        return profileFieldSectionEndpoint.getAllProfileFieldSections();
    }

}
