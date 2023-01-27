package com.opencbs.genesis.services;

import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.EventDto;
import com.opencbs.genesis.dto.EventParticipantsDto;
import com.opencbs.genesis.dto.requests.EventRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * Created by alopatin on 19-Apr-17.
 */
public interface EventService {
    Page<EventParticipantsDto> findAvailableParticipants(String query, User currentUser, Pageable pageable);
    EventDto create(EventRequest request, User currentUser);
    EventDto update(EventRequest request, User user);
    List<EventDto> findEvents(Long participantId, Date startDate, Date endDate);
    Page<EventDto> findApplicationEvents(Long id, Pageable pageable);
}
