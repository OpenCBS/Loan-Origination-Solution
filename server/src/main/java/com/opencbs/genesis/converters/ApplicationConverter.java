package com.opencbs.genesis.converters;

import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.domain.State;
import com.opencbs.genesis.domain.Workflow;
import com.opencbs.genesis.domain.permissions.ApplicationStatePermission;
import com.opencbs.genesis.dto.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Makhsut Islamov on 30.03.2017.
 */
public class ApplicationConverter {
    @SuppressWarnings("ConstantConditions")
    public static ApplicationDto toDto(Application application) {
        ApplicationDto dto = toDto(application, ApplicationDto.class);

        List<SimpleInfoDto> actionDtos = application.getCurrentState().getActions()
                .stream().map(x -> new SimpleInfoDto(x.getId(), x.getName(), x.getDescription()))
                .collect(Collectors.toList());
        dto.setActions(actionDtos);

        return dto;
    }

    @SuppressWarnings("ConstantConditions")
    public static ApplicationSyncDto toSyncDto(Application application) {
        ApplicationSyncDto dto = toDto(application, ApplicationSyncDto.class);
        dto.setFieldValues(application.getFieldValues());
        return dto;
    }

    public static  List<ApplicationSimpleDto> toListDto(List<Application> applications){
        return applications.stream().map(x -> toDto(x, ApplicationSimpleDto.class)).collect(Collectors.toList());
    }


    private static <T extends ApplicationSimpleDto> T toDto(Application application, Class<T> clazz) {
        try {
            Workflow workflow = application.getWorkflow();
            State currentState = application.getCurrentState();
            List<ApplicationStatePermission> currentStatePermissions =application.getCurrentStatePermissions();
            List<PermissionDto> currentPermissions = StatePermissionConverter.toPermissionsDto(currentStatePermissions);

            T dto = clazz.newInstance();
            dto.setId(application.getId());
            dto.setName(application.getName());
            dto.setWorkflowInfo(new SimpleInfoDto(workflow.getId(), workflow.getName(), workflow.getDescription()));
            dto.setProfile(application.getProfile());
            dto.setCurrentState(new StateSimpleDto(
                    currentState.getId(), currentState.getName(), currentState.getDescription(), currentPermissions)
            );
            dto.setCreatedUser(
                    new SimpleInfoDto(application.getCreatedUser().getId(), application.getCreatedUser().getUsername())
            );
            dto.setResponsibleUser(
                    new SimpleInfoDto(application.getResponsibleUser().getId(), application.getResponsibleUser().getUsername())
            );
            dto.setCreatedDate(application.getCreatedDate());
            dto.setCompleted(application.isCompleted());
            dto.setIsDeleted(application.isDeleted());
            dto.setComment(application.getComment());

            return dto;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
