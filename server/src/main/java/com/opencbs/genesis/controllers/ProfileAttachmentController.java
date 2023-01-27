package com.opencbs.genesis.controllers;

import com.opencbs.genesis.endpoint.profile.ProfileEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/profiles/{profileId}/{sectionCode}/{profileFieldCode}/attachment")
public class ProfileAttachmentController {

    private final ProfileEndpoint profileEndpoint;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity get(@PathVariable Long profileId,
                              @PathVariable String sectionCode,
                              @PathVariable String profileFieldCode,
                              @RequestParam(value = "size", required = false) Integer size) throws Exception {
        return profileEndpoint.getResponseEntity(profileId, sectionCode, profileFieldCode, size);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public void post(@RequestParam("file") MultipartFile file,
                     @PathVariable Long profileId,
                     @PathVariable String sectionCode,
                     @PathVariable String profileFieldCode) {
        profileEndpoint.createAttachment(file, profileId, sectionCode, profileFieldCode);
    }
}