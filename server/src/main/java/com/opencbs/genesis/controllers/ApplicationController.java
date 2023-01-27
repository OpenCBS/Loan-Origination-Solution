package com.opencbs.genesis.controllers;

import com.opencbs.genesis.domain.*;
import com.opencbs.genesis.domain.enums.NotificationType;
import com.opencbs.genesis.domain.enums.SystemPermissions;
import com.opencbs.genesis.dto.ApplicationDto;
import com.opencbs.genesis.dto.ApplicationSimpleDto;
import com.opencbs.genesis.dto.CommentDto;
import com.opencbs.genesis.dto.requests.ActionRequest;
import com.opencbs.genesis.dto.requests.ApplicationEditRequest;
import com.opencbs.genesis.dto.requests.ApplicationRequest;
import com.opencbs.genesis.dto.responses.ApiResponse;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.helpers.DateHelper;
import com.opencbs.genesis.security.RequiresPermission;
import com.opencbs.genesis.services.ApplicationAttachmentService;
import com.opencbs.genesis.services.ApplicationService;
import com.opencbs.genesis.services.NotificationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Makhsut Islamov on 25.10.2016.
 */
@RestController
@RequestMapping(value = "/api/applications")
public class ApplicationController extends BaseController{
    private final ApplicationService applicationService;
    private final NotificationHistoryService notificationHistoryService;
    private final ApplicationAttachmentService applicationAttachmentService;

    @Autowired
    public ApplicationController(ApplicationService applicationService,NotificationHistoryService notificationHistoryService,ApplicationAttachmentService applicationAttachmentService) {
        this.applicationService = applicationService;
        this.notificationHistoryService = notificationHistoryService;
        this.applicationAttachmentService = applicationAttachmentService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermission(SystemPermissions.READ_APPLICATION)
    public ApiResponse<Page<ApplicationSimpleDto>> get(@RequestParam  Map<String, String> filters, Pageable pageable) throws ApiException {
        return ReturnResponse(applicationService.findBy(filters, pageable));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ApiResponse<ApplicationDto> get(@PathVariable Long id, Principal principal) throws ApiException {
        return ReturnResponse(applicationService.findBy(id, (User) principal));
    }

    @RequestMapping(method = RequestMethod.POST)
    @RequiresPermission(SystemPermissions.CREATE_APPLICATION)
    public ApiResponse<ApplicationDto> post(@RequestBody ApplicationRequest request) throws ApiException {
        return ReturnResponse(applicationService.create(request));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @RequiresPermission(SystemPermissions.EDIT_APPLICATION)
    public ApiResponse<ApplicationDto> put(@RequestBody ApplicationEditRequest request, @PathVariable Long id) throws ApiException {
        return ReturnResponse(applicationService.update(id, request));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @RequiresPermission(SystemPermissions.DELETE_APPLICATION)
    public ApiResponse<ApplicationDto> delete(@PathVariable Long id, @RequestBody CommentDto commentDto) throws ApiException {
        return ReturnResponse(applicationService.delete(id, commentDto.getComment()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ApiResponse<Boolean> post(@RequestBody ActionRequest request, @PathVariable Long id, Principal principal)
                                                                                                throws ApiException {
        return ReturnResponse(applicationService.performAction(request, id, (User) principal));
    }

    @Scheduled(fixedDelay = 86400000)
    public void AddNotificationHistory() throws ApiException {
        System.out.println("Schedule");
        State prospect = new State(1L);
        State coaching = new State(3L);

        Date weekAgoStartDay = DateHelper.getStartOfDay(DateHelper.addDays(new Date(),-7));
        Date weekAgoEndDay = DateHelper.getEndOfDay(DateHelper.addDays(new Date(),-7));
        Date twoWeeksAgoStartDay = DateHelper.getStartOfDay(DateHelper.addDays(new Date(),-14));
        Date twoWeeksAgoEndDay = DateHelper.getEndOfDay(DateHelper.addDays(new Date(),-14));
        List<Application> prospectApplications = applicationService.findAllByCurrentState(prospect,weekAgoStartDay,weekAgoEndDay);
        List<Application> coachingApplications = applicationService.findAllByCurrentState(coaching,twoWeeksAgoStartDay,twoWeeksAgoEndDay);

        for (Application application:  prospectApplications) {
            NotificationHistory notificationHistory= new NotificationHistory();
            notificationHistory.setCreatedBy("Admin");
            notificationHistory.setCreatedDate(new Date());
            notificationHistory.setNotificationType(NotificationType.WARNING);
            notificationHistory.setRecipients("");
            notificationHistory.setTitle(application.getName() + "remains more than 7 days in the state \"Prospect\".");
            notificationHistory.setContent("");
            notificationHistoryService.save(notificationHistory);
        }

        for (Application application:  coachingApplications) {
            List<ApplicationAttachment> applicationAttachments =
                    applicationAttachmentService.findByDateRange(application.getId(),twoWeeksAgoStartDay,new Date());
            if(applicationAttachments.size() > 0) continue;
            NotificationHistory notificationHistory= new NotificationHistory();
            notificationHistory.setCreatedBy("SYSTEM");
            notificationHistory.setCreatedDate(new Date());
            notificationHistory.setNotificationType(NotificationType.WARNING);
            notificationHistory.setRecipients("");
            notificationHistory.setTitle(application.getName() + "remains more than 14 days in the state \"Coaching\".");
            notificationHistory.setContent("");
            notificationHistoryService.save(notificationHistory);
        }
    }
}