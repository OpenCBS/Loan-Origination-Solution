package com.opencbs.genesis.validators;

import com.opencbs.genesis.domain.BaseEntity;
import com.opencbs.genesis.domain.Event;
import com.opencbs.genesis.domain.Profile;
import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.EventParticipantsDto;
import com.opencbs.genesis.dto.IdDto;
import com.opencbs.genesis.dto.requests.EventRequest;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.repositories.EventRepository;
import com.opencbs.genesis.repositories.ProfileRepository;
import com.opencbs.genesis.repositories.UserRepository;
import com.opencbs.genesis.validators.helpers.ParamsHelper;
import io.jsonwebtoken.lang.Assert;
import org.hibernate.internal.util.collections.CollectionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by alopatin on 19-Apr-17.
 */

@Component
public class EventRequestValidator extends  BaseValidator {
    private UserRepository userRepository;
    private ProfileRepository profileRepository;
    private EventRepository eventRepository;

    @Autowired
    public EventRequestValidator(UserRepository userRepository,
                                 ProfileRepository profileRepository,
                                 EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    protected void validateInternal(Object... params) throws ApiException {
        EventRequest eventRequest = ParamsHelper.getAs(params, 0);
        boolean isEditMode = eventRequest.getId() != null && eventRequest.getId() > 0;

        Assert.isTrue(!StringUtils.isEmpty(eventRequest.getTitle()), "title is required");
        Assert.isTrue(!StringUtils.isEmpty(eventRequest.getDescription()), "description is required");

        validateDates(eventRequest);
        validateParticipants(eventRequest.getParticipants());
        if(isEditMode){
            validateAuthor(eventRequest,ParamsHelper.getAs(params,1));
        }
    }

    private void validateAuthor(EventRequest eventRequest, User currentUser){
        Event dbEvent = this.eventRepository.findOne(eventRequest.getId());
        Assert.isTrue(dbEvent!= null, String.format("can't find event with id %d", eventRequest.getId()));
        Assert.isTrue(dbEvent.getCreatedUser().getId() == currentUser.getId(), "you can't edit event");
    }

    private void validateDates(EventRequest eventRequest) throws ApiException {
        Assert.isTrue(eventRequest.getStart() != null, "start date is required");
        Assert.isTrue(eventRequest.getEnd() != null, "end date is required");
        Assert.isTrue(eventRequest.getNotify() != null, "notify date is required");
        Assert.isTrue(eventRequest.getStart().before(eventRequest.getEnd())
                || eventRequest.getStart().equals(eventRequest.getEnd()), "start date can't be me more than end date");
    }

    private void validateParticipants(List<EventParticipantsDto> participants) throws ApiException {
        Assert.isTrue(CollectionHelper.isNotEmpty(participants), "participants is required");

        validateParticipantsAsUsers(participants.stream().filter(EventParticipantsDto::getIsUser).map(IdDto::getId).collect(Collectors.toList()));
        validateParticipantsAsProfiles(participants.stream().filter(participant -> !participant.getIsUser()).map(IdDto::getId).collect(Collectors.toList()));
    }

    private void validateParticipantsAsUsers(List<Long> participantsAsUsers) throws ApiException {
        List<User> users = userRepository.findAllByIdInAndEnabledTrue(participantsAsUsers);
        List<Long> participantsInDb = users.stream().map(BaseEntity::getId).collect(Collectors.toList());

        List<String> notExistsParticipants = getNotExistParticipants(participantsAsUsers, participantsInDb);
        Assert.isTrue(notExistsParticipants.size() == 0, String.format("next users don't exist: %s", String.join(", ", notExistsParticipants)));
    }

    private void validateParticipantsAsProfiles(List<Long> participantsAsProfiles) throws ApiException {
        List<Profile> profiles = profileRepository.findAllByIdIn(participantsAsProfiles);
        List<Long> participantsInDb = profiles.stream().map(BaseEntity::getId).collect(Collectors.toList());

        List<String> notExistsParticipants = getNotExistParticipants(participantsAsProfiles, participantsInDb);
        Assert.isTrue(notExistsParticipants.size() == 0, String.format("next profiles don't exist: %s", String.join(", ", notExistsParticipants)));
    }

    private List<String> getNotExistParticipants(List<Long> participants, List<Long> participantsInDb) throws ApiException {
        List<String> notExistParticipants = new ArrayList<>();

        if (CollectionHelper.isEmpty(participantsInDb)) {
            notExistParticipants.addAll(participants.stream().map(Object::toString).collect(Collectors.toList()));
        } else if (participantsInDb.size() != participants.size()) {

            participants.forEach(participantId -> {
                if (participantsInDb.stream().noneMatch(id -> Objects.equals(id, participantId))) {
                    notExistParticipants.add(participantId.toString());
                }
            });
        }

        return notExistParticipants;
    }
}
