package com.opencbs.genesis.validators;

import com.opencbs.genesis.domain.Profile;
import com.opencbs.genesis.exceptions.ValidationException;
import com.opencbs.genesis.helpers.EntityHelper;
import com.opencbs.genesis.repositories.ProfileRepository;
import com.opencbs.genesis.validators.helpers.ParamsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


/**
 * Created by Makhsut Islamov on 24.10.2016.
 */
@Component
public class ProfileValidator extends BaseValidator{
    private ProfileRepository profileRepository;

    @Autowired
    public ProfileValidator(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    protected void validateInternal(Object ... params) throws ValidationException {
        Profile entity = ParamsHelper.getAs(params, 0);
        Long updatableId = ParamsHelper.getAs(params, 1);
        entity.setId(updatableId);

        if(EntityHelper.isPersist(entity)){
            Assert.isTrue(profileRepository.exists(entity.getId()), "Entity not found.");
        }
    }
}
