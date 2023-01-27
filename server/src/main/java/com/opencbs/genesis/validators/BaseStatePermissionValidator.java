package com.opencbs.genesis.validators;

import com.opencbs.genesis.domain.State;
import com.opencbs.genesis.domain.Workflow;
import com.opencbs.genesis.dto.StatePermissionDto;
import com.opencbs.genesis.helpers.ListHelper;
import com.opencbs.genesis.repositories.RoleRepository;
import com.opencbs.genesis.repositories.UserRepository;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Makhsut Islamov on 14.12.2016.
 */
public abstract class BaseStatePermissionValidator extends BaseValidator {
    protected final RoleRepository roleRepository;
    protected final UserRepository userRepository;

    protected BaseStatePermissionValidator(RoleRepository roleRepository, UserRepository userRepository){
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    protected void validateStatePermissions(Workflow workflow, List<StatePermissionDto> statePermissions){
        if(CollectionUtils.isEmpty(statePermissions)) return;

        statePermissions.forEach(statePermissionDto -> {
            State state = ListHelper.findFirstOrDefault(workflow.getStates(),
                    x -> x.getId() == statePermissionDto.getId());
            Assert.notNull(state, "State with {id: "+ statePermissionDto.getId()+"} not found.");

            statePermissionDto.getPermissions().forEach(permission -> {
                Assert.isTrue(permission.getRoleId() != null || permission.getUserId() != null,
                        "roleId or userId of permission field must provided");
                if(permission.getRoleId() != null){
                    Assert.isTrue(roleRepository.exists(permission.getRoleId()),
                            "Role with {id: "+permission.getRoleId()+"} not found.");
                }

                if(permission.getUserId() != null){
                    Assert.isTrue(userRepository.exists(permission.getUserId()),
                            "User with {id: "+permission.getUserId()+"} not found.");
                }
            });
        });
    }
}