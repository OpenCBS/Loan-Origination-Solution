package com.opencbs.genesis.controllers;

import com.opencbs.genesis.dto.ApplicationLogDto;
import com.opencbs.genesis.dto.responses.ApiResponse;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.services.ApplicationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Makhsut Islamov on 16.12.2016.
 */
@RestController
@RequestMapping(value = "/api/applications/{id}/logs")
public class ApplicationLogController extends BaseController {
    private final ApplicationLogService applicationLogService;

    @Autowired
    public ApplicationLogController(ApplicationLogService applicationLogService) {
        this.applicationLogService = applicationLogService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ApiResponse<List<ApplicationLogDto>> get(@PathVariable Long id) throws ApiException {
        return ReturnResponse(applicationLogService.findAllOf(id));
    }
}