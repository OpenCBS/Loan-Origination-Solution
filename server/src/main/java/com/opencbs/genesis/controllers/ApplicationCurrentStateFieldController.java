package com.opencbs.genesis.controllers;

import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.FieldSectionDto;
import com.opencbs.genesis.dto.requests.FieldValue;
import com.opencbs.genesis.dto.responses.ApiResponse;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.services.ApplicationFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Created by Makhsut Islamov on 06.12.2016.
 */
@RestController
@RequestMapping(value = "/api/applications/{id}/current-state/fields")
public class ApplicationCurrentStateFieldController extends BaseController {
    private final ApplicationFieldService applicationFieldService;

    @Autowired
    public ApplicationCurrentStateFieldController(ApplicationFieldService applicationFieldService) {
        this.applicationFieldService = applicationFieldService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ApiResponse<List<FieldSectionDto>> get(@PathVariable Long id, Principal principal) throws ApiException {
        return ReturnResponse(applicationFieldService.getCurrentStateFields(id, (User) principal));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ApiResponse<List<FieldSectionDto>> post(@PathVariable Long id, @RequestBody List<FieldValue> fieldValues, Principal principal)
                                                                                                    throws ApiException {
        return ReturnResponse(applicationFieldService.updateCurrentStateFields(id, fieldValues, (User) principal));
    }
}