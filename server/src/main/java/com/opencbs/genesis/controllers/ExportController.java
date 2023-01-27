package com.opencbs.genesis.controllers;

import com.opencbs.genesis.domain.enums.ExportEntity;
import com.opencbs.genesis.domain.enums.SystemPermissions;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.security.RequiresPermission;
import com.opencbs.genesis.services.ApplicationService;
import com.opencbs.genesis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by alopatin on 11-May-17.
 */

@Controller
@RequestMapping(value = "/api/export")
public class ExportController {
    private final ApplicationService applicationService;
    private final UserService userService;

    @Autowired
    ExportController(ApplicationService applicationService,
                     UserService userService) {
        this.applicationService = applicationService;
        this.userService = userService;
    }

    @RequestMapping(value = "/applications", method = RequestMethod.GET)
    @RequiresPermission(SystemPermissions.READ_APPLICATION)
    public String getApplications(@RequestParam Map<String, String> filters, Pageable pageable, Model model) throws ApiException {
        model.addAttribute(ExportEntity.APPLICATIONS.toString(), this.applicationService.findBy(filters,pageable));
        return "";
    }

    @RequestMapping(value = "/volunteers", method = RequestMethod.GET)
    public String getVolunteers(Model model) throws ApiException {
        model.addAttribute(ExportEntity.VOLUNTEERS.toString(), this.userService.findVolunteers());
        return "";
    }
}
