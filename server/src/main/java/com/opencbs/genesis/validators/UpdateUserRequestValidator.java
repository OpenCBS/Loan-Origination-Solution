package com.opencbs.genesis.validators;

import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.requests.UpdateUserRequest;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.repositories.UserRepository;
import com.opencbs.genesis.validators.helpers.EmailValidator;
import com.opencbs.genesis.validators.helpers.ParamsHelper;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * Created by alopatin on 25-Apr-17.
 */

@Component
public class UpdateUserRequestValidator extends BaseValidator {
    private final UserRepository userRepository;

    @Autowired
    public UpdateUserRequestValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void validateInternal(Object... params) throws ApiException {
        User currentUser = ParamsHelper.getAs(params, 0);
        UpdateUserRequest entity = ParamsHelper.getAs(params, 1);

        Assert.isTrue(currentUser != null, "can't find current user");
        Assert.isTrue(entity != null, "can't find current request");

        Assert.isTrue(!StringUtils.isEmpty(entity.getFirstName()), "firstName is required field.");
        Assert.isTrue(!StringUtils.isEmpty(entity.getLastName()), "lastName is required field.");
        EmailValidator.validate(entity.getEmail());

        User updatableUser = userRepository.findOne(currentUser.getId());
        Assert.isTrue(updatableUser != null, String.format("User with {id: %d} not found.", currentUser.getId()));


        User userWithEmail = userRepository.findOneByEmailAndEnabledTrue(entity.getEmail());
        Assert.isTrue(userWithEmail == null || Objects.equals(userWithEmail.getId(), currentUser.getId()), "email has already used");
    }
}
