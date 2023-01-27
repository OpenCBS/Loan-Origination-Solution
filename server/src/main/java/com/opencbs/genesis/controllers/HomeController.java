package com.opencbs.genesis.controllers;

import com.opencbs.genesis.security.AllowAnonymous;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Makhsut Islamov on 18.10.2016.
 */
@RestController
public class HomeController {
    @RequestMapping("/api")
    @AllowAnonymous
    public String index(){
        return "Welcome to Genesis API!";
    }
}
