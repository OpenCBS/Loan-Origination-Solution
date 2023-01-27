package com.opencbs.genesis.controllers;

import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.OnlineApplyDto;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.services.OnlineApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/api/")
public class OnlineApplyController {

    private final OnlineApplyService onlineApplyService;

    @Autowired
    public OnlineApplyController(OnlineApplyService onlineApplyService) {
        this.onlineApplyService = onlineApplyService;
    }

    @RequestMapping(value = "apply", method = POST)
    public void create(@RequestBody OnlineApplyDto o, @AuthenticationPrincipal Principal principal) throws ApiException {
        this.onlineApplyService.create(o, (User) principal);
    }
}
