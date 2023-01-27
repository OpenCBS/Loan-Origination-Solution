package com.opencbs.genesis.controllers;

import com.opencbs.genesis.domain.enums.SystemPermissions;
import com.opencbs.genesis.dto.StatePermissionDto;
import com.opencbs.genesis.dto.responses.ApiResponse;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.security.RequiresPermission;
import com.opencbs.genesis.services.ApplicationPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Makhsut Islamov on 13.12.2016.
 */
@RestController
@RequestMapping(value = "/api/applications/{id}/permissions")
public class ApplicationPermissionController extends BaseController {
    private final ApplicationPermissionService applicationPermissionService;

    @Autowired
    public ApplicationPermissionController(ApplicationPermissionService applicationPermissionService) {
        this.applicationPermissionService = applicationPermissionService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermission(SystemPermissions.READ_APPLICATION_PERMISSIONS)
    public ApiResponse<List<StatePermissionDto>> get(@PathVariable Long id) throws ApiException {
        return ReturnResponse(applicationPermissionService.findBy(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    @RequiresPermission(SystemPermissions.EDIT_APPLICATION_PERMISSIONS)
    public ApiResponse<List<StatePermissionDto>> post(@PathVariable Long id, @RequestBody List<StatePermissionDto> permissions)
                                                                                                        throws ApiException {
        return ReturnResponse(applicationPermissionService.recreate(id, permissions));
    }
}