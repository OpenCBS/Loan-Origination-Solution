package com.opencbs.genesis.repositories;

import com.opencbs.genesis.domain.Event;
import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.EventParticipantsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * Created by alopatin on 19-Apr-17.
 */
public interface EventRepository extends Repository<Event> {
    Page<EventParticipantsDto> findAvailableParticipants(String query, User currentUser, Pageable pageable);
    List<Event> findAllBy(Long participantId, Date startDate, Date endDate);
    Page<Event> findByIdIn(List<Long> eventsIds, Pageable pageable);
}
