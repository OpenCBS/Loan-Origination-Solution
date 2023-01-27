package com.opencbs.genesis.controllers;

import com.opencbs.genesis.domain.enums.SystemPermissions;
import com.opencbs.genesis.dto.FieldSectionDto;
import com.opencbs.genesis.dto.requests.FieldValue;
import com.opencbs.genesis.dto.responses.ApiResponse;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.security.RequiresPermission;
import com.opencbs.genesis.services.ApplicationFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Makhsut Islamov on 13.12.2016.
 */
@RestController
@RequestMapping(value = "/api/applications/{id}/fields")
public class ApplicationFieldController extends BaseController {
    private final ApplicationFieldService applicationFieldService;

    @Autowired
    public ApplicationFieldController(ApplicationFieldService applicationFieldService) {
        this.applicationFieldService = applicationFieldService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermission(SystemPermissions.READ_APPLICATION_FIELDS)
    public ApiResponse<List<FieldSectionDto>> get(@PathVariable Long id) throws ApiException {
        return ReturnResponse(applicationFieldService.getAllFields(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    @RequiresPermission(SystemPermissions.EDIT_APPLICATION_FIELDS)
    public ApiResponse<List<FieldSectionDto>> post(@PathVariable Long id, @RequestBody List<FieldValue> fieldValues)
            throws ApiException {
        return ReturnResponse(applicationFieldService.updateFields(id, fieldValues));
    }
}