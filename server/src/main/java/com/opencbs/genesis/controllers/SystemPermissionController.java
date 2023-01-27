package com.opencbs.genesis.controllers;

import com.opencbs.genesis.domain.enums.SystemPermissions;
import com.opencbs.genesis.dto.CodeNameDto;
import com.opencbs.genesis.dto.responses.ApiResponse;
import com.opencbs.genesis.security.RequiresPermission;
import com.opencbs.genesis.services.SystemPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Makhsut Islamov on 16.12.2016.
 */
@RestController
@RequestMapping(value = "/api/permissions")
public class SystemPermissionController extends BaseController {
    private final SystemPermissionService systemPermissionService;

    @Autowired
    public SystemPermissionController(SystemPermissionService systemPermissionService) {
        this.systemPermissionService = systemPermissionService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermission(SystemPermissions.READ_ROLE)
    public ApiResponse<List<CodeNameDto>> get(){
        return ReturnResponse(systemPermissionService.getAll());
    }
}