package com.opencbs.genesis.validators;

import com.opencbs.genesis.domain.Workflow;
import com.opencbs.genesis.dto.requests.ApplicationRequest;
import com.opencbs.genesis.exceptions.ValidationException;
import com.opencbs.genesis.repositories.ProfileRepository;
import com.opencbs.genesis.repositories.RoleRepository;
import com.opencbs.genesis.repositories.UserRepository;
import com.opencbs.genesis.repositories.WorkflowRepository;
import com.opencbs.genesis.validators.helpers.ParamsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Created by Makhsut Islamov on 25.10.2016.
 */
@Component
public class ApplicationRequestValidator extends BaseStatePermissionValidator {
    private final ProfileRepository profileRepository;
    private final WorkflowRepository workflowRepository;

    @Autowired
    public ApplicationRequestValidator(ProfileRepository profileRepository,
                                       WorkflowRepository workflowRepository,
                                       UserRepository userRepository,
                                       RoleRepository roleRepository) {
        super(roleRepository, userRepository);
        this.profileRepository = profileRepository;
        this.workflowRepository = workflowRepository;
    }

    @Override
    protected void validateInternal(Object ... params) throws ValidationException {
        ApplicationRequest entity = ParamsHelper.getAs(params, 0);

        Assert.isTrue(profileRepository.exists(entity.getProfileId()), "profileId is required field.");

        Workflow workflow = workflowRepository.findOne(entity.getWorkflowId());
        Assert.notNull(workflow, "workflowId is required field");

        Assert.isTrue(userRepository.exists(entity.getResponsibleUserId()),
                "User with {id: "+entity.getResponsibleUserId()+"} not found.");

        Assert.isTrue(!StringUtils.isEmpty(entity.getName()), "name is required field.");

        validateStatePermissions(workflow, entity.getStatePermissions());
    }
}
