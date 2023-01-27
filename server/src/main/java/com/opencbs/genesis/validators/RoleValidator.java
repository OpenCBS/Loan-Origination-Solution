package com.opencbs.genesis.validators;

import com.opencbs.genesis.domain.Role;
import com.opencbs.genesis.exceptions.ValidationException;
import com.opencbs.genesis.helpers.EntityHelper;
import com.opencbs.genesis.repositories.RoleRepository;
import com.opencbs.genesis.validators.helpers.ParamsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by Makhsut Islamov on 03.11.2016.
 */
@Component
public class RoleValidator extends BaseValidator{
    private RoleRepository roleRepository;
    private final String DEFAULT_ROLE = "ADMIN";

    @Autowired
    public RoleValidator(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    protected void validateInternal(Object... params) throws ValidationException {
        Role entity = ParamsHelper.getAs(params, 0);
        Long updatableId = ParamsHelper.getAs(params, 1);
        entity.setId(updatableId);

        Assert.notNull(entity.getCode(), "code is required field.");
        Assert.notNull(entity.getName(), "name is required field.");

        Role role = roleRepository.findOneByCode(entity.getCode());

        if(EntityHelper.isPersist(entity)){
            Assert.isTrue(role == null || role.getId().equals(entity.getId()), "code must be unique.");
            Assert.isTrue(roleRepository.exists(entity.getId()), "Entity not found.");
            Assert.isTrue(!DEFAULT_ROLE.equals(entity.getCode()), "Forbidden to modify default role 'ADMIN'.");
        }else {
            Assert.isNull(role, "Code must be unique.");
        }
    }
}