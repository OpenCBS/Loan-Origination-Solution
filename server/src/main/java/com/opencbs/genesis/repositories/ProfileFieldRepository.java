package com.opencbs.genesis.repositories;

import com.opencbs.genesis.domain.ProfileField;
import com.opencbs.genesis.domain.ProfileFieldSection;

import java.util.List;
import java.util.Optional;

public interface ProfileFieldRepository extends Repository<ProfileField> {
    Optional<ProfileField> findByCodeAndSectionCode(String code, String sectionCode);

    List<ProfileField> findBySection(ProfileFieldSection profileFieldSection);
}
