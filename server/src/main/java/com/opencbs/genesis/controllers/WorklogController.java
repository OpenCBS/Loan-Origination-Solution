package com.opencbs.genesis.controllers;

import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.domain.Worklog;
import com.opencbs.genesis.domain.enums.SystemPermissions;
import com.opencbs.genesis.dto.requests.WorklogRequest;
import com.opencbs.genesis.dto.responses.ApiResponse;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.security.RequiresPermission;
import com.opencbs.genesis.services.WorklogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Created by Makhsut Islamov on 21.11.2016.
 */
@RestController
@RequestMapping(value = "/api/applications/{id}/worklogs")
public class WorklogController extends BaseController {
    private final WorklogService worklogService;

    @Autowired
    public WorklogController(WorklogService worklogService) {
        this.worklogService = worklogService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermission(SystemPermissions.READ_APPLICATION_WORKLOGS)
    public ApiResponse<Page<Worklog>> get(@PathVariable Long id, Pageable pageable) throws ApiException {
        return ReturnResponse(worklogService.findBy(id, pageable));
    }

    @RequestMapping(method = RequestMethod.POST)
    @RequiresPermission(SystemPermissions.CREATE_APPLICATION_WORKLOGS)
    public ApiResponse<Worklog> post(@PathVariable Long id, @RequestBody WorklogRequest worklog, Principal principal) throws ApiException {
        return ReturnResponse(worklogService.create(id, worklog, (User) principal));
    }

    @RequestMapping(value = "/{worklogId}", method = RequestMethod.PUT)
    @RequiresPermission(SystemPermissions.EDIT_APPLICATION_WORKLOGS)
    public ApiResponse<Worklog> edit(@PathVariable Long id, @PathVariable Long worklogId, @RequestBody WorklogRequest worklog, Principal principal) throws ApiException {
        return ReturnResponse(worklogService.edit(id, worklogId, worklog, (User) principal));
    }
}
