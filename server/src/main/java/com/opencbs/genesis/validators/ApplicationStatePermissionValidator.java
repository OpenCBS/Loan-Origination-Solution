package com.opencbs.genesis.validators;

import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.dto.StatePermissionDto;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.repositories.ApplicationRepository;
import com.opencbs.genesis.repositories.RoleRepository;
import com.opencbs.genesis.repositories.UserRepository;
import com.opencbs.genesis.validators.helpers.ParamsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by Makhsut Islamov on 14.12.2016.
 */
@Component
public class ApplicationStatePermissionValidator extends BaseStatePermissionValidator {
    private final ApplicationRepository applicationRepository;

    @Autowired
    protected ApplicationStatePermissionValidator(RoleRepository roleRepository, UserRepository userRepository,
                                                  ApplicationRepository applicationRepository) {
        super(roleRepository, userRepository);
        this.applicationRepository = applicationRepository;
    }

    @Override
    protected void validateInternal(Object... params) throws ApiException {
        Long applicationId = ParamsHelper.getAs(params, 0);
        List<StatePermissionDto> permissions = ParamsHelper.getAs(params, 1);

        Application application = applicationRepository.findOne(applicationId);
        Assert.notNull(application, String.format("Application with {id: %d} not found", applicationId));

        validateStatePermissions(application.getWorkflow(), permissions);
    }
}