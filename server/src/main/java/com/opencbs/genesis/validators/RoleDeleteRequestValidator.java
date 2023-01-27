package com.opencbs.genesis.validators;

import com.opencbs.genesis.domain.Role;
import com.opencbs.genesis.exceptions.ValidationException;
import com.opencbs.genesis.repositories.RoleRepository;
import com.opencbs.genesis.repositories.UserRepository;
import com.opencbs.genesis.validators.helpers.ParamsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by user on 08.11.2016.
 */
@Component
public class RoleDeleteRequestValidator extends BaseValidator{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public RoleDeleteRequestValidator(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    protected void validateInternal(Object... params) throws ValidationException {
        Long id = ParamsHelper.getAs(params, 0);

        Role role = roleRepository.findOne(id);
        Assert.notNull(role, "Entity not found.");

        Assert.isTrue(!userRepository.existsByRoleId(role.getId()), "Can not delete role. It has references by users.");
    }
}
