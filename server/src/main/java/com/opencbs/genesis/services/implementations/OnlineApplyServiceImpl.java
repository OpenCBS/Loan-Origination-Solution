package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.domain.Profile;
import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.domain.WorkflowField;
import com.opencbs.genesis.dto.ApplicationDto;
import com.opencbs.genesis.dto.OnlineApplyDto;
import com.opencbs.genesis.dto.PermissionDto;
import com.opencbs.genesis.dto.StatePermissionDto;
import com.opencbs.genesis.dto.requests.ApplicationRequest;
import com.opencbs.genesis.dto.requests.FieldValue;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.repositories.WorkFlowFieldRepository;
import com.opencbs.genesis.services.ApplicationFieldService;
import com.opencbs.genesis.services.ApplicationService;
import com.opencbs.genesis.services.OnlineApplyService;
import com.opencbs.genesis.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OnlineApplyServiceImpl implements OnlineApplyService {

    private final ProfileService profileService;
    private final ApplicationService applicationService;
    private final ApplicationFieldService applicationFieldService;
    private final WorkFlowFieldRepository workFlowFieldRepository;

    @Override
    @Transactional
    public void create(OnlineApplyDto applyDto, User currentUser) throws ApiException {
        Profile profile = this.saveProfile(applyDto);

        ApplicationDto applicationDto = this.saveApplication(profile, applyDto, currentUser);

        List<FieldValue> fieldValues = new ArrayList<>();

        for (WorkflowField field : this.workFlowFieldRepository.findAll()) {
            FieldValue fieldValue = applyDto.getFields()
                    .stream()
                    .filter(x -> x.getFieldId() == field.getId()).findFirst()
                    .orElseGet(() -> {
                        FieldValue valueSupplier = new FieldValue();
                        valueSupplier.setFieldId(field.getId());
                        valueSupplier.setValue("");
                        return valueSupplier;
                    });
            fieldValues.add(fieldValue);
        }

        this.applicationFieldService.updateFields(applicationDto.getId(), fieldValues);
    }
    private Profile saveProfile(OnlineApplyDto applyDto) throws ApiException {
        final ModelMapper mapper  = new ModelMapper();
        Profile profile =  mapper.map(applyDto, Profile.class);
        return this.profileService.create(profile);
    }

    private ApplicationDto saveApplication(Profile profile, OnlineApplyDto applyDto, User user) throws ApiException {
        ApplicationRequest applicationRequest = new ApplicationRequest();
        applicationRequest.setName(String.format("Online application %s %s", applyDto.getFirstName(), applyDto.getLastName()));
        applicationRequest.setProfileId(profile.getId());
        applicationRequest.setWorkflowId(1L);
        applicationRequest.setResponsibleUserId(user.getId());

        List<StatePermissionDto> statePermissionDtos = new ArrayList<>();

        StatePermissionDto statePermissionDto = new StatePermissionDto();
        statePermissionDto.setId(1L);
        List<PermissionDto> permissionDtos = new ArrayList<>();
        PermissionDto permissionDto = new PermissionDto();
        permissionDto.setRoleId(2L);
        permissionDtos.add(permissionDto);
        permissionDto = new PermissionDto();
        permissionDto.setUserId(1L);
        permissionDtos.add(permissionDto);
        statePermissionDto.setPermissions(permissionDtos);
        statePermissionDtos.add(statePermissionDto);

        applicationRequest.setStatePermissions(statePermissionDtos);
        return applicationService.create(applicationRequest);
    }
}
