package com.opencbs.genesis.converters;

import com.opencbs.genesis.domain.Event;
import com.opencbs.genesis.dto.EventDto;
import com.opencbs.genesis.dto.SimpleInfoDto;
import com.opencbs.genesis.dto.requests.EventRequest;
import org.hibernate.internal.util.collections.CollectionHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alopatin on 19-Apr-17.
 */
public class EventConverter {
    public static EventDto toDto(Event event){
        EventDto eventDto = new EventDto();

        eventDto.setId(event.getId());
        eventDto.setTitle(event.getTitle());
        eventDto.setDescription(event.getContent());
        eventDto.setStart(event.getStartDate());
        eventDto.setEnd(event.getEndDate());
        eventDto.setParticipants(EventParticipantConverter.toDtoList(event.getParticipants()));
        eventDto.setCreatedDate(event.getCreatedDate());
        eventDto.setCreatedUser(new SimpleInfoDto(event.getCreatedUser().getId(),event.getCreatedUser().getFullName()));
        eventDto.setAllDay(event.getAllDay());
        eventDto.setNotify(event.getNotifyAt());

        return eventDto;
    }

    public static List<EventDto> toDtoList(List<Event> events) {
        if (CollectionHelper.isEmpty(events)) {
            return new ArrayList<>();
        }
        List<EventDto> eventDtos = new ArrayList<>();
        events.forEach(event -> eventDtos.add(toDto(event)));
        return eventDtos;
    }

    public static Event toEntity(Event event, EventRequest eventRequest){
        event.setId(eventRequest.getId());
        event.setTitle(eventRequest.getTitle());
        event.setContent(eventRequest.getDescription());
        event.setStartDate(eventRequest.getStart());
        event.setEndDate(eventRequest.getEnd());
        event.setParticipants(EventParticipantConverter.toEntityList(event, eventRequest.getParticipants(),true));
        event.setAllDay(eventRequest.isAllDay());
        event.setNotifyAt(eventRequest.getNotify());

        return event;
    }


    public  static Event toEntity(EventRequest eventRequest) {
        Event event = new Event();
        return toEntity(event, eventRequest);
    }
}
