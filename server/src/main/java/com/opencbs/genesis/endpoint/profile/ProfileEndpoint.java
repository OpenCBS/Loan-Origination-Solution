package com.opencbs.genesis.endpoint.profile;

import com.opencbs.genesis.dto.profile.ProfileCreateDto;
import com.opencbs.genesis.dto.profile.ProfileDto;
import com.opencbs.genesis.dto.profile.ProfileUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileEndpoint {
    ProfileDto create(ProfileCreateDto createDto);

    Page<ProfileDto> findAll(Pageable pageable);

    ProfileDto findById(Long id);

    ProfileDto update(Long id, ProfileUpdateDto profileUpdateDto);

    void delete(Long id);

    void createAttachment(MultipartFile file, Long profileId,
                          String sectionCode, String profileFieldCode);

    ResponseEntity getResponseEntity(Long profileId, String sectionCode, String profileFieldCode, Integer size) throws Exception;
}
