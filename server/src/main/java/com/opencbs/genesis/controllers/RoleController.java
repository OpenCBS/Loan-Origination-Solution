package com.opencbs.genesis.controllers;

import com.opencbs.genesis.domain.Role;
import com.opencbs.genesis.domain.enums.SystemPermissions;
import com.opencbs.genesis.dto.responses.ApiResponse;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.security.RequiresPermission;
import com.opencbs.genesis.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Makhsut Islamov on 03.11.2016.
 */
@RestController
@RequestMapping(value = "/api/roles")
public class RoleController extends BaseController{
    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermission(SystemPermissions.READ_ROLE)
    public ApiResponse<Page<Role>> get(Pageable pageable){
        return ReturnResponse(roleService.findAll(pageable));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @RequiresPermission(SystemPermissions.READ_ROLE)
    public ApiResponse<Role> get(@PathVariable Long id){
        return ReturnResponse(roleService.findById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    @RequiresPermission(SystemPermissions.CREATE_ROLE)
    public ApiResponse<Role> post(@RequestBody Role role) throws ApiException {
        return ReturnResponse(roleService.create(role));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @RequiresPermission(SystemPermissions.EDIT_ROLE)
    public ApiResponse<Role> put(@RequestBody Role role, @PathVariable Long id) throws ApiException {
        return ReturnResponse(roleService.update(role, id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ApiResponse<Role> delete(@PathVariable Long id) throws ApiException {
        return ReturnResponse(roleService.delete(id));
    }
}
