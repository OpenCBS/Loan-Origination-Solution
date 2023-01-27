package com.opencbs.genesis.controllers;

import com.opencbs.genesis.domain.Notification;
import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.responses.ApiResponse;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Created by Askat on 12/8/2016.
 */
@RestController
@RequestMapping(value = "api/applications")
public class NotificationController extends BaseController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RequestMapping(value = "/notifications", method = RequestMethod.GET)
    public ApiResponse<Page<Notification>> get(Pageable pageable) throws ApiException {
        return ReturnResponse(notificationService.findAll(pageable));
    }

    @RequestMapping(value = "/notifications/{date}", method = RequestMethod.GET)
    public ApiResponse<Page<Notification>> get(@PathVariable String date, Pageable pageable) throws ApiException {
        return ReturnResponse(notificationService.findByNotificationDate(date, pageable));
    }

    @RequestMapping(value = "/{id}/notifications", method = RequestMethod.GET)
    public ApiResponse<Page<Notification>> get(@PathVariable Long id, Pageable pageable) throws ApiException {
        return ReturnResponse(notificationService.findBy(id, pageable));
    }

    @RequestMapping(value = "/{id}/notifications", method = RequestMethod.POST)
    public ApiResponse<Notification> post(@PathVariable Long id, @RequestBody Notification notification, Principal principal) throws ApiException {
        return ReturnResponse(notificationService.create(id, notification, (User) principal));
    }

    @RequestMapping(value = "/notifications/{id}")
    public ApiResponse<Notification> put(@RequestBody Notification notification, @PathVariable Long id) {
        return ReturnResponse(notificationService.update(notification, id));
    }
}
