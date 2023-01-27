package com.opencbs.genesis.controllers;

import com.opencbs.genesis.dto.EventDto;
import com.opencbs.genesis.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by alopatin on 03-Jul-17.
 */

@RestController
@RequestMapping(value = "/api/applications/{id}/events")
public class ApplicationEventController extends BaseController {
   private final EventService eventService;

   @Autowired
   public ApplicationEventController(EventService eventService){
       this.eventService = eventService;
   }

   @RequestMapping(method = RequestMethod.GET)
    public Page<EventDto> get(@PathVariable Long id, Pageable pageable){
        return this.eventService.findApplicationEvents(id,pageable);
    }
}
