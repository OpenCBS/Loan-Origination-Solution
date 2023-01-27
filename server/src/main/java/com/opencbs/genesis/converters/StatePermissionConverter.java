package com.opencbs.genesis.converters;

import com.opencbs.genesis.domain.*;
import com.opencbs.genesis.domain.permissions.*;
import com.opencbs.genesis.dto.PermissionDto;
import com.opencbs.genesis.dto.StatePermissionDto;
import lombok.Data;
import org.hibernate.internal.util.collections.CollectionHelper;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Makhsut Islamov on 14.12.2016.
 */
@Data
public class StatePermissionConverter {
    public static List<ApplicationStatePermission> toEntityList(Application application, List<StatePermissionDto> permissions) {
        List<ApplicationStatePermission> applicationStatePermissions = new ArrayList<>();
        if (CollectionUtils.isEmpty(permissions)) {
            return applicationStatePermissions;
        }

        permissions.forEach(x -> x.getPermissions().forEach(permission -> {
            ApplicationStatePermission applicationStatePermission;
            if (permission.getUserId() != null) {
                applicationStatePermission = new UserApplicationStatePermission();
                ((UserApplicationStatePermission) applicationStatePermission).setUser(new User(permission.getUserId()));
            } else {
                applicationStatePermission = new RoleApplicationStatePermission();
                ((RoleApplicationStatePermission) applicationStatePermission).setRole(new Role(permission.getRoleId()));
            }

            applicationStatePermission.setApplication(application);
            applicationStatePermission.setState(new State(x.getId()));

            applicationStatePermissions.add(applicationStatePermission);
        }));

        return applicationStatePermissions;
    }

    public static List<ApplicationStatePermission> toEntityList(Application application, List<StatePermissionDto> permissions, Workflow workflow) {
        permissions = toEntityList(permissions,workflow.getStates());
        return toEntityList(application,permissions);
    }

    private static List<StatePermissionDto> toEntityList(List<StatePermissionDto> permissions, List<State> states) {
        if (permissions == null) {
            permissions = new ArrayList<>();
        }

        if (CollectionUtils.isEmpty(states)) {
            return permissions;
        }

        for (State state : states) {
            if (permissions.stream().noneMatch(perm -> perm.getId() == state.getId())) {
                List<WorkflowStatePermission> workflowStatePermissions = state.getPermissions();
                if (workflowStatePermissions == null) {
                    continue;
                }

                StatePermissionDto statePermission = new StatePermissionDto();
                statePermission.setId(state.getId());

                List<PermissionDto> statePermissions = workflowStatePermissions.stream().map(StatePermissionConverter::toPermissionsDto).collect(Collectors.toList());
                statePermission.setPermissions(statePermissions);

                permissions.add(statePermission);
            }
        }

        return permissions;
    }


    public static List<StatePermissionDto> toDtoList(List<ApplicationStatePermission> permissions){
        if(CollectionUtils.isEmpty(permissions)){
            return new ArrayList<>();
        }

        Map<State, List<ApplicationStatePermission>> grouped = permissions
                .stream().collect(Collectors.groupingBy(ApplicationStatePermission::getState));

        List<StatePermissionDto> result = new ArrayList<>();
        grouped.forEach((state, applicationStatePermissions) -> {
            StatePermissionDto dto = new StatePermissionDto();
            dto.setId(state.getId());
            dto.setPermissions(new ArrayList<>());

            applicationStatePermissions.forEach(applicationStatePermission -> {
                dto.getPermissions().add(toPermissionsDto(applicationStatePermission));
            });
            result.add(dto);
        });

        return result;
    }

    public static List<PermissionDto> toPermissionsDto(List<ApplicationStatePermission> applicationStatePermissions){
        if(CollectionUtils.isEmpty(applicationStatePermissions)){
            return new ArrayList<>();
        }

        List<PermissionDto> result = new ArrayList<>();
        applicationStatePermissions.forEach(applicationStatePermission->{
            PermissionDto permissionDto = toPermissionsDto(applicationStatePermission);
            result.add(permissionDto);
        });

        return result;
    }

    private static PermissionDto toPermissionsDto(ApplicationStatePermission applicationStatePermission){
        PermissionDto permissionDto = new PermissionDto();
        if(RoleApplicationStatePermission.class.isInstance(applicationStatePermission)){
            Role role = ((RoleApplicationStatePermission)applicationStatePermission).getRole();
            permissionDto.setRoleId(role.getId());
            permissionDto.setName(role.getName());
        }else {
            User user = ((UserApplicationStatePermission)applicationStatePermission).getUser();
            permissionDto.setUserId(user.getId());
            permissionDto.setName(user.getFullName());
        }

        return permissionDto;
    }

    private static PermissionDto toPermissionsDto(WorkflowStatePermission workflowStatePermission){
        PermissionDto permissionDto = new PermissionDto();
        if(RoleWorkflowStatePermission.class.isInstance(workflowStatePermission)){
            Role role = ((RoleWorkflowStatePermission)workflowStatePermission).getRole();
            permissionDto.setRoleId(role.getId());
            permissionDto.setName(role.getName());
        }else {
            User user = ((UserWorkflowStatePermission)workflowStatePermission).getUser();
            permissionDto.setUserId(user.getId());
            permissionDto.setName(user.getFullName());
        }

        return permissionDto;
    }
}