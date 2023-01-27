package com.opencbs.genesis.dto.requests;

import com.opencbs.genesis.dto.EventParticipantsDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by alopatin on 19-Apr-17.
 */
@Data
public class EventRequest {
    private Long id;
    private String title;
    private String description;

    private Date start;
    private Date end;
    private Date notify;
    private List<EventParticipantsDto> participants;
    private boolean allDay;
}
