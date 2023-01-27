package com.opencbs.genesis.repositories;

import com.opencbs.genesis.domain.Profile;
import com.opencbs.genesis.domain.ProfileField;
import com.opencbs.genesis.domain.ProfileFieldValue;

import java.util.List;
import java.util.Optional;

public interface ProfileFieldValueRepository extends Repository<ProfileFieldValue> {
    List<ProfileFieldValue> findByOwnerAndField(Profile profile, ProfileField profileField);

    Optional<ProfileFieldValue> getByOwnerAndField(Profile profile, ProfileField profileField);

    void deleteAllByOwner(Profile profile);

    void deleteByField(Profile profile , ProfileField profileField);
}
