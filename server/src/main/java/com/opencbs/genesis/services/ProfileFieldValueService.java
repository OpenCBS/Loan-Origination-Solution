package com.opencbs.genesis.services;

import com.opencbs.genesis.domain.Profile;
import com.opencbs.genesis.domain.ProfileField;
import com.opencbs.genesis.domain.ProfileFieldValue;
import com.opencbs.genesis.request.profilefieldrequest.ProfileFieldValueCreateRequest;

import java.util.List;
import java.util.Optional;

public interface ProfileFieldValueService {
    ProfileFieldValue create(ProfileFieldValueCreateRequest profileFieldValueCreateRequest);

    ProfileFieldValue update(ProfileFieldValue profileFieldValue, String newValue);

    List<ProfileFieldValue> findByProfileAndField(Profile profile, ProfileField profileField);

    Optional<ProfileFieldValue> getByOwner(Profile profile, ProfileField profileField);

    void delete(Profile profile);

    void deleteByField(Profile profile , ProfileField profileField);
}
