package com.opencbs.genesis.validators;

import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.requests.UserRequest;
import com.opencbs.genesis.exceptions.ValidationException;
import com.opencbs.genesis.repositories.RoleRepository;
import com.opencbs.genesis.repositories.UserRepository;
import com.opencbs.genesis.validators.helpers.EmailValidator;
import com.opencbs.genesis.validators.helpers.ParamsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * Created by Makhsut Islamov on 25.10.2016.
 */
@Component
public class UserRequestValidator extends BaseValidator {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserRequestValidator(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    protected void validateInternal(Object... params) throws ValidationException {
        UserRequest entity = ParamsHelper.getAs(params, 0);
        Long updatableUserId = ParamsHelper.getAs(params, 1);

        Assert.isTrue(!StringUtils.isEmpty(entity.getUsername()), "username is required field.");
        Assert.isTrue(entity.getUsername().length() > 2, "minimum length of username is 3.");
        Assert.isTrue(!StringUtils.isEmpty(entity.getFirstName()), "firstName is required field.");
        Assert.isTrue(!StringUtils.isEmpty(entity.getLastName()), "lastName is required field.");
        EmailValidator.validate(entity.getEmail());

        Assert.notNull(entity.getRoleId(), "roleId is required field.");
        Assert.isTrue(roleRepository.exists(entity.getRoleId()),
                String.format("Role with {id: %d} not found.", entity.getRoleId()));

        if (updatableUserId == null) {
            onCreate(entity);
        } else {
            onUpdate(entity, updatableUserId);
        }

        validateEmail(entity,updatableUserId);
    }

    private void onCreate(UserRequest entity) {
        Assert.isNull(userRepository.findOneByUsernameAndEnabledTrue(entity.getUsername()), "username must be unique.");
        validatePassword(entity.getPassword());
    }

    private void onUpdate(UserRequest entity, Long id){
        User updatableUser = userRepository.findOne(id);
        Assert.isTrue(updatableUser != null, String.format("User with {id: %d} not found.", id));

        User possibleDuplicateUser = userRepository.findOneByUsername(entity.getUsername());
        Assert.isTrue(
                possibleDuplicateUser == null || possibleDuplicateUser.getId().equals(updatableUser.getId()),
                "username must be unique."
        );

        if(!StringUtils.isEmpty(entity.getPassword())){
            validatePassword(entity.getPassword());
        }
    }

    private void validatePassword(String password){
        Assert.isTrue(!StringUtils.isEmpty(password), "password is required field.");
        Assert.isTrue(password.length() > 5, "minimum length of password is 6.");
    }

    private void validateEmail(UserRequest entity, Long updatableUserId){
        User userWithEmail = userRepository.findOneByEmailAndEnabledTrue(entity.getEmail());

        if(updatableUserId == null){
            Assert.isTrue(userWithEmail == null, "email has already used");
        }else{
            Assert.isTrue(userWithEmail == null || Objects.equals(userWithEmail.getId(), updatableUserId), "email has already used");
        }
    }
}