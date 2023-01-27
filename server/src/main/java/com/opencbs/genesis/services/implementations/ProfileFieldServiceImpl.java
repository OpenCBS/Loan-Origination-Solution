package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.domain.ProfileField;
import com.opencbs.genesis.domain.ProfileFieldSection;
import com.opencbs.genesis.repositories.ProfileFieldRepository;
import com.opencbs.genesis.services.ProfileFieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProfileFieldServiceImpl extends BaseService implements ProfileFieldService {

    private final ProfileFieldRepository profileFieldRepository;

    @Override
    public Optional<ProfileField> findByCodeAndSectionCode(String code, String sectionCode) {
        return profileFieldRepository.findByCodeAndSectionCode(code, sectionCode);
    }

    @Override
    public List<ProfileField> getAllBySection(ProfileFieldSection profileFieldSection) {
        return profileFieldRepository.findBySection(profileFieldSection);
    }
}
