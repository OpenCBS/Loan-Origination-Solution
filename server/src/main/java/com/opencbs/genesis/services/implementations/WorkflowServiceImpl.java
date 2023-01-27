package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.domain.Workflow;
import com.opencbs.genesis.domain.WorkflowFieldSection;
import com.opencbs.genesis.domain.permissions.RoleWorkflowStatePermission;
import com.opencbs.genesis.domain.permissions.UserWorkflowStatePermission;
import com.opencbs.genesis.dto.PermissionDto;
import com.opencbs.genesis.dto.StateDto;
import com.opencbs.genesis.repositories.WorkflowRepository;
import com.opencbs.genesis.services.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Makhsut Islamov on 19.10.2016.
 */

@Service
public class WorkflowServiceImpl extends BaseService implements WorkflowService{
    private final WorkflowRepository workflowRepository;

    @Autowired
    public WorkflowServiceImpl(WorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    @Override
    public Page<Workflow> findAll(Pageable pageable) {
        return workflowRepository.findAll(pageable);
    }

    @Override
    public List<WorkflowFieldSection> getFieldSectionsOf(Long workflowId) {
        Workflow workflow = workflowRepository.findOne(workflowId);
        Assert.notNull(workflow, "Entity not found");
        return workflow.getSections();
    }

    @Override
    public List<StateDto> getStatesOf(Long workflowId) {
        Workflow workflow = workflowRepository.findOne(workflowId);
        Assert.notNull(workflow, "Entity not found");

        List<StateDto> result = new ArrayList<>();
        workflow.getStates().forEach(state -> {
            StateDto dto = new StateDto();
            dto.setId(state.getId());
            dto.setName(state.getName());
            dto.setDescription(state.getDescription());
            dto.setPermissions(new ArrayList<>());

            state.getPermissions().forEach(workflowStatePermission -> {
                PermissionDto permissionDto = new PermissionDto();
                if(RoleWorkflowStatePermission.class.isInstance(workflowStatePermission)){
                    permissionDto.setRoleId(((RoleWorkflowStatePermission)workflowStatePermission).getRole().getId());
                }else {
                    User user = ((UserWorkflowStatePermission)workflowStatePermission).getUser();
                    permissionDto.setUserId(user.getId());
                }
                dto.getPermissions().add(permissionDto);
            });
            result.add(dto);
        });

        return result;
    }

    @Override
    public Workflow findById(Long id) {
        return workflowRepository.findOne(id);
    }
}