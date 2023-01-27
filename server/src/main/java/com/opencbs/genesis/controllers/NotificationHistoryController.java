package com.opencbs.genesis.controllers;

import com.opencbs.genesis.domain.NotificationHistory;
import com.opencbs.genesis.domain.enums.SystemPermissions;
import com.opencbs.genesis.dto.responses.ApiResponse;
import com.opencbs.genesis.security.RequiresPermission;
import com.opencbs.genesis.services.NotificationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by alopatin on 05-Jul-17.
 */

@RestController
@RequestMapping(value = "/api/notification-history")
public class NotificationHistoryController extends BaseController{
    private final NotificationHistoryService notificationHistoryService;

    @Autowired
    public NotificationHistoryController(NotificationHistoryService notificationHistoryService){
        this.notificationHistoryService = notificationHistoryService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermission(SystemPermissions.READ_PROFILE)
    public ApiResponse<Page<NotificationHistory>> get(@RequestParam(value = "query", required = false) String query, Pageable pageable){
        return ReturnResponse(notificationHistoryService.findBy(query, pageable));
    }
}
