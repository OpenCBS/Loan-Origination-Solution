package com.opencbs.genesis.validators;

import com.opencbs.genesis.domain.Profile;
import com.opencbs.genesis.exceptions.ValidationException;
import com.opencbs.genesis.repositories.ApplicationRepository;
import com.opencbs.genesis.repositories.ProfileRepository;
import com.opencbs.genesis.validators.helpers.ParamsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by Makhsut Islamov on 08.11.2016.
 */
@Component
public class ProfileDeleteRequestValidator extends BaseValidator {
    private final ProfileRepository profileRepository;
    private final ApplicationRepository applicationRepository;

    @Autowired
    public ProfileDeleteRequestValidator(ProfileRepository profileRepository, ApplicationRepository applicationRepository) {
        this.profileRepository = profileRepository;
        this.applicationRepository = applicationRepository;
    }

    @Override
    protected void validateInternal(Object... params) throws ValidationException {
        Long id = ParamsHelper.getAs(params, 0);

        Profile profile = profileRepository.findOne(id);
        Assert.notNull(profile, "Entity not found.");

        Assert.isTrue(!applicationRepository.existsByProfileId(profile.getId()),
                "Can not delete profile. It has references by applications.");
    }
}
