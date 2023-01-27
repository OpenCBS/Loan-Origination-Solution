package com.opencbs.genesis.controllers;

import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.EventDto;
import com.opencbs.genesis.dto.EventParticipantsDto;
import com.opencbs.genesis.dto.requests.EventRequest;
import com.opencbs.genesis.dto.responses.ApiResponse;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.helpers.DateHelper;
import com.opencbs.genesis.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;


/**
 * Created by alopatin on 19-Apr-17.
 */

@RestController
@RequestMapping(value = "/api/events")
public class EventController extends BaseController {
    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(value = "/participants", method = RequestMethod.GET)
    public ApiResponse<Page<EventParticipantsDto>> getParticipants(@RequestParam(value = "search", required = false) String search, Principal principal, Pageable pageable) {
        return ReturnResponse(eventService.findAvailableParticipants(search, (User) principal, pageable));
    }

    @RequestMapping(value = "/participant/{id}/date/{date}", method = RequestMethod.GET)
    public ApiResponse<List<EventDto>> get(@RequestParam(value = "monthNum", required = false) Integer monthNum, @PathVariable Long id, @PathVariable String date) throws ApiException {
        Date startDate = DateHelper.getStartOfMonth(DateHelper.convert(date));
        Date endDate = DateHelper.getEndOfDay(DateHelper.getEndOfMonth(startDate));

        if (monthNum == null) {
            monthNum = 1;
        }

        if (monthNum == 3) {
            Date nextMonth = DateHelper.addMonth(startDate, 1);
            endDate = DateHelper.getEndOfDay(DateHelper.getEndOfMonth(nextMonth));
            startDate = DateHelper.getStartOfMonth(DateHelper.addMonth(startDate, -1));
        }

        return ReturnResponse(eventService.findEvents(id, startDate, endDate));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ApiResponse<EventDto> post(@RequestBody EventRequest request, Principal principal) throws ApiException {
        return ReturnResponse(eventService.create(request, (User) principal));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ApiResponse<EventDto> put(@RequestBody EventRequest request, Principal principal) throws ApiException {
        User currentUser = (User) principal;
        return ReturnResponse(eventService.update(request, currentUser));
    }
}
