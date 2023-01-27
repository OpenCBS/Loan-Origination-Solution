package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.domain.Profile;
import com.opencbs.genesis.domain.ProfileField;
import com.opencbs.genesis.domain.ProfileFieldValue;
import com.opencbs.genesis.repositories.ProfileFieldValueRepository;
import com.opencbs.genesis.request.profilefieldrequest.ProfileFieldValueCreateRequest;
import com.opencbs.genesis.services.ProfileFieldValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileFieldValueServiceImpl implements ProfileFieldValueService {

    private final ProfileFieldValueRepository profileFieldValueRepository;

    @Override
    @Transactional
    public ProfileFieldValue create(ProfileFieldValueCreateRequest profileFieldValueCreateRequest) {
        ProfileFieldValue profileFieldValue = new ProfileFieldValue();
        profileFieldValue.setField(profileFieldValueCreateRequest.getProfileField());
        profileFieldValue.setOwner(profileFieldValueCreateRequest.getOwner());
        profileFieldValue.setSectionCode(profileFieldValueCreateRequest.getSectionCode());
        profileFieldValue.setValue(profileFieldValueCreateRequest.getValue());
        return profileFieldValueRepository.save(profileFieldValue);
    }

    @Override
    @Transactional
    public ProfileFieldValue update(ProfileFieldValue profileFieldValue, String newValue) {
        profileFieldValue.setValue(newValue);
        return profileFieldValueRepository.save(profileFieldValue);
    }

    @Override
    public List<ProfileFieldValue> findByProfileAndField(Profile profile, ProfileField profileField) {
        return profileFieldValueRepository.findByOwnerAndField(profile, profileField);
    }

    @Override
    public Optional<ProfileFieldValue> getByOwner(Profile profile, ProfileField profileField) {
        return profileFieldValueRepository.getByOwnerAndField(profile, profileField);
    }

    @Override
    public void delete(Profile profile) {
        profileFieldValueRepository.deleteAllByOwner(profile);
    }

    @Override
    public void deleteByField(Profile profile, ProfileField profileField) {
        profileFieldValueRepository.deleteByField(profile, profileField);
    }
}