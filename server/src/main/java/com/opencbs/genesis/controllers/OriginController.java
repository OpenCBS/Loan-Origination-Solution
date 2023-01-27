package com.opencbs.genesis.controllers;

import com.opencbs.genesis.domain.Origin;
import com.opencbs.genesis.services.OriginService;
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
@RequestMapping(value = "/api/origins")
public class OriginController extends BaseController{
    private final OriginService originService;

    @RequestMapping(method = GET)
    public List<Origin> get() {
            return this.originService.findAll();
        }

    @RequestMapping(value = "/lookup", method = GET)
    public Page<Origin> getLookup(Pageable pageable, @RequestParam(value = "search",required = false) String searchString) {
        return this.originService.findAll(pageable, searchString);
    }
}
