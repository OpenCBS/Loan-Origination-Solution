package com.opencbs.genesis.services;

import com.opencbs.genesis.domain.*;
import com.opencbs.genesis.dto.EventDto;
import com.opencbs.genesis.dto.EventParticipantsDto;

import java.util.List;

/**
 * Created by alopatin on 12-Apr-17.
 */
public interface EmailNotificationService {
    void stateHasBeenChanged(Action action, Activity activity, Application application);

    void eventCreated(EventDto event, List<EventParticipantsDto> participants, User currentUser);

    void eventUpdated(EventDto event, List<EventParticipantsDto> participants, User user);

    void sendTokenForResetPassword(User user, String token);

    void attachmentAdded(ApplicationAttachment applicationAttachment);

    void worklogAdded(Worklog worklog);

}
