package com.opencbs.genesis.controllers;

import com.opencbs.genesis.domain.Language;
import com.opencbs.genesis.services.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/languages")
public class LanguageController extends BaseController{

    private final LanguageService languageService;

    @RequestMapping(method = GET)
    public List<Language> get() {
        return this.languageService.findAll();
    }

    @RequestMapping(value = "/lookup", method = GET)
    public Page<Language> getLookup(Pageable pageable, @RequestParam(value = "search",required = false) String searchString) {
        return this.languageService.findAll(pageable, searchString);
    }
}