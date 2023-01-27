package com.opencbs.genesis.converters;

import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.domain.Event;
import com.opencbs.genesis.domain.Profile;
import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.domain.events.EventParticipants;
import com.opencbs.genesis.domain.events.ProfileEventParticipants;
import com.opencbs.genesis.domain.events.UserEventParticipants;
import com.opencbs.genesis.dto.EventParticipantsDto;
import org.hibernate.internal.util.collections.CollectionHelper;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alopatin on 19-Apr-17.
 */
public class EventParticipantConverter {
    public static List<EventParticipants> toEntityList(Event event, List<EventParticipantsDto> participants, boolean isUnique) {
        List<EventParticipants> eventParticipants = new ArrayList<>();
        if (CollectionUtils.isEmpty(participants)) {
            return eventParticipants;
        }

        if (isUnique) {
            ArrayList<EventParticipantsDto> filtered = new ArrayList<>();
            participants.forEach(participant -> {
                if (filtered.stream().noneMatch(f -> f.getId() == participant.getId() &&
                        f.getIsUser() == participant.getIsUser())) {
                    filtered.add(participant);
                }
            });

            participants = filtered;
        }

        participants.forEach(participant -> {
            EventParticipants eventParticipant;

            if (participant.getIsUser()) {
                eventParticipant = new UserEventParticipants();
                ((UserEventParticipants) eventParticipant).setUser(new User(participant.getId()));
            } else {
                eventParticipant = new ProfileEventParticipants();
                ((ProfileEventParticipants) eventParticipant).setProfile(new Profile(participant.getId()));
                ((ProfileEventParticipants) eventParticipant).setApplication(new Application(participant.getApplicationId()));
            }
            eventParticipant.setEvent(event);
            eventParticipants.add(eventParticipant);
        });

        return eventParticipants;
    }

    public static List<EventParticipantsDto> toDtoList(List<EventParticipants> eventParticipants) {
        List<EventParticipantsDto> result = new ArrayList<>();
        if (CollectionHelper.isEmpty(eventParticipants)) {
            return result;
        }

        eventParticipants.forEach(participant -> {
            EventParticipantsDto eventParticipantsDto = new EventParticipantsDto();

            if (UserEventParticipants.class.isInstance(participant)) {
                User user = ((UserEventParticipants) participant).getUser();

                eventParticipantsDto.setId(user.getId());
                eventParticipantsDto.setIsUser(true);
                eventParticipantsDto.setName(user.getFullName());
            } else {
                Profile profile = ((ProfileEventParticipants) participant).getProfile();
                eventParticipantsDto.setId(profile.getId());
                eventParticipantsDto.setIsUser(false);
            }

            result.add(eventParticipantsDto);
        });

        return result;
    }
}
