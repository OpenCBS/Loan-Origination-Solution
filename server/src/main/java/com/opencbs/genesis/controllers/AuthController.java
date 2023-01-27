package com.opencbs.genesis.controllers;

import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.requests.LoginRequest;
import com.opencbs.genesis.dto.responses.ApiResponse;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.helpers.TokenHelper;
import com.opencbs.genesis.security.AllowAnonymous;
import com.opencbs.genesis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Makhsut Islamov on 21.10.2016.
 */
@RestController
@RequestMapping(value = "/api/auth")
public class AuthController extends BaseController{
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @AllowAnonymous
    public ApiResponse<String> auth(@RequestBody LoginRequest request) throws ApiException {
        User user = userService.auth(request.getUsername(), request.getPassword());
        return ReturnResponse(TokenHelper.generateToken(user.getUsername()));
    }
}
