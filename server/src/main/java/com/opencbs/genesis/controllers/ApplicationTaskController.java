package com.opencbs.genesis.controllers;

import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.ApplicationSimpleDto;
import com.opencbs.genesis.dto.responses.ApiResponse;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * Created by Makhsut Islamov on 16.12.2016.
 */
@RestController
@RequestMapping(value = "/api/applications/tasks")
public class ApplicationTaskController extends BaseController {
    private final ApplicationService applicationService;

    @Autowired
    public ApplicationTaskController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ApiResponse<List<ApplicationSimpleDto>> get(Principal principal, @RequestParam(value = "query", required = false) String query) throws ApiException {
        return ReturnResponse(applicationService.getTaskList((User) principal, query));
    }
}