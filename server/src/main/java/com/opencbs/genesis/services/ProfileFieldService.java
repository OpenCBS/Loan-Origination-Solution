package com.opencbs.genesis.services;

import com.opencbs.genesis.domain.ProfileField;
import com.opencbs.genesis.domain.ProfileFieldSection;
import com.opencbs.genesis.exceptions.ApiException;

import java.util.List;
import java.util.Optional;

public interface ProfileFieldService {

    Optional<ProfileField> findByCodeAndSectionCode(String code, String sectionCOde);

    List<ProfileField> getAllBySection(ProfileFieldSection profileFieldSection);
}
