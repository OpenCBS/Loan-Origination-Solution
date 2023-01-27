package com.opencbs.genesis.controllers;

import com.opencbs.genesis.domain.enums.SystemPermissions;
import com.opencbs.genesis.dto.profile.ProfileCreateDto;
import com.opencbs.genesis.dto.profile.ProfileDto;
import com.opencbs.genesis.dto.profile.ProfileUpdateDto;
import com.opencbs.genesis.endpoint.profile.ProfileEndpoint;
import com.opencbs.genesis.security.RequiresPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Makhsut Islamov on 24.10.2016.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/profiles")
public class ProfileController extends BaseController {

    private final ProfileEndpoint profileEndpoint;

    @GetMapping()
    public Page<ProfileDto> get(Pageable pageable) {
        return profileEndpoint.findAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public ProfileDto get(@PathVariable Long id) {
        return profileEndpoint.findById(id);
    }

    @PostMapping()
    @RequiresPermission(SystemPermissions.CREATE_PROFILE)
    public ProfileDto create(@RequestBody @Validated ProfileCreateDto createDto) {
        return profileEndpoint.create(createDto);
    }

    @PutMapping(value = "/{id}")
    public ProfileDto update(@PathVariable Long id, @RequestBody ProfileUpdateDto profileUpdateDto) {
        return profileEndpoint.update(id, profileUpdateDto);
    }

    @DeleteMapping(value = "/{id}")
    @RequiresPermission(SystemPermissions.DELETE_PROFILE)
    public void delete(@PathVariable Long id) {
        profileEndpoint.delete(id);
    }
}