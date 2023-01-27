package com.opencbs.genesis.services;

import com.opencbs.genesis.domain.Workflow;
import com.opencbs.genesis.domain.WorkflowFieldSection;
import com.opencbs.genesis.dto.StateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Makhsut Islamov on 19.10.2016.
 */
public interface WorkflowService {
    Page<Workflow> findAll(Pageable pageable);
    List<WorkflowFieldSection> getFieldSectionsOf(Long workflowId);
    List<StateDto> getStatesOf(Long workflowId);
    Workflow findById(Long id);
}