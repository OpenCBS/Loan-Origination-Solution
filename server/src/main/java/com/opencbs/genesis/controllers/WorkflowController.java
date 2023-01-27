package com.opencbs.genesis.controllers;

import com.opencbs.genesis.domain.Workflow;
import com.opencbs.genesis.domain.WorkflowFieldSection;
import com.opencbs.genesis.dto.StateDto;
import com.opencbs.genesis.dto.responses.ApiResponse;
import com.opencbs.genesis.services.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Makhsut Islamov on 09.11.2016.
 */
@RestController
@RequestMapping(value = "/api/workflows")
public class WorkflowController extends BaseController {
    private final WorkflowService workflowService;

    @Autowired
    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ApiResponse<Page<Workflow>> get(Pageable pageable){
        return ReturnResponse(workflowService.findAll(pageable));
    }

    @RequestMapping(value = "/{id}/field-sections", method = RequestMethod.GET)
    public ApiResponse<List<WorkflowFieldSection>> getSections(@PathVariable Long id){
        return ReturnResponse(workflowService.getFieldSectionsOf(id));
    }

    @RequestMapping(value = "/{id}/states", method = RequestMethod.GET)
    public ApiResponse<List<StateDto>> getStates(@PathVariable Long id){
        return ReturnResponse(workflowService.getStatesOf(id));
    }
}