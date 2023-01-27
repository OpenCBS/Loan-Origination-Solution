package com.opencbs.genesis.controllers;

import com.opencbs.genesis.domain.Country;
import com.opencbs.genesis.services.CountryService;

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
@RequestMapping(value = "/api/countries")
public class CountryController extends BaseController{
    private final CountryService countryService;

    @RequestMapping(method = GET)
    public List<Country> get() {
        return this.countryService.findAll();
    }

    @RequestMapping(value = "/lookup", method = GET)
    public Page<Country> getLookup(Pageable pageable, @RequestParam(value = "search",required = false) String searchString) {
        return this.countryService.findAll(pageable, searchString);
    }
}
