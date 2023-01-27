package com.opencbs.genesis.controllers;

import com.opencbs.genesis.domain.ApplicationAttachment;
import com.opencbs.genesis.domain.enums.SystemPermissions;
import com.opencbs.genesis.dto.responses.ApiResponse;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.security.AllowAnonymous;
import com.opencbs.genesis.security.RequiresPermission;
import com.opencbs.genesis.services.ApplicationAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Makhsut Islamov on 21.11.2016.
 */
@RestController
public class ApplicationAttachmentController extends BaseController {
    private final ApplicationAttachmentService applicationAttachmentService;

    @Autowired
    public ApplicationAttachmentController(ApplicationAttachmentService applicationAttachmentService) {
        this.applicationAttachmentService = applicationAttachmentService;
    }

    @RequestMapping(value = "/api/applications/{id}/attachments", method = RequestMethod.GET)
    @RequiresPermission(SystemPermissions.READ_APPLICATION_ATTACHMENTS)
    public ApiResponse<Page<ApplicationAttachment>> get(@PathVariable Long id, Pageable pageable) throws ApiException {
        return ReturnResponse(applicationAttachmentService.findBy(id, pageable));
    }

    @RequestMapping(value = "/api/applications/{id}/attachments/{name:.+}",  method = RequestMethod.GET)
    @AllowAnonymous
    @ResponseBody
    public ResponseEntity<Resource> get(@PathVariable Long id, @PathVariable String name) throws ApiException {
        //TODO: add permission SystemPermissions.READ_APPLICATION_ATTACHMENT
        Resource file = applicationAttachmentService.findAttachment(id, name);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + name + "\"")
                .body(file);
    }

    @RequestMapping(value = "/api/applications/{id}/attachments", method = RequestMethod.POST)
    @RequiresPermission(SystemPermissions.CREATE_APPLICATION_ATTACHMENTS)
    public ApiResponse<ApplicationAttachment> post(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws ApiException {
        return ReturnResponse(applicationAttachmentService.create(id, file));
    }

    @RequestMapping(value = "/api/attachments/{id}", method = RequestMethod.DELETE)
    @RequiresPermission(SystemPermissions.DELETE_APPLICATION_ATTACHMENTS)
    public ApiResponse<ApplicationAttachment> delete(@PathVariable Long id) throws ApiException {
        return ReturnResponse(applicationAttachmentService.delete(id));
    }
}