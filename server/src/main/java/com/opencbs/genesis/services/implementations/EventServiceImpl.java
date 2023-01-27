package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.converters.EventConverter;
import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.domain.Event;
import com.opencbs.genesis.domain.Profile;
import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.domain.events.EventParticipants;
import com.opencbs.genesis.domain.events.ProfileEventParticipants;
import com.opencbs.genesis.dto.EventDto;
import com.opencbs.genesis.dto.EventParticipantsDto;
import com.opencbs.genesis.dto.IdDto;
import com.opencbs.genesis.dto.requests.EventRequest;
import com.opencbs.genesis.repositories.*;
import com.opencbs.genesis.services.EmailNotificationService;
import com.opencbs.genesis.services.EventService;
import com.opencbs.genesis.validators.ApplicationEventRequestValidator;
import com.opencbs.genesis.validators.EventRequestValidator;
import com.opencbs.genesis.validators.annotations.ValidateWith;
import org.hibernate.internal.util.collections.CollectionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alopatin on 19-Apr-17.
 */

@Service
public class EventServiceImpl extends BaseService implements EventService {
    private EventRepository eventRepository;
    private EmailNotificationService emailNotificationService;
    private UserRepository userRepository;
    private ProfileRepository profileRepository;
    private EventParticipantsRepository eventParticipantsRepository;
    private ApplicationRepository applicationRepository;
    private ProfileEventParticipantsRepository profileEventParticipantsRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository,
                            EmailNotificationService emailNotificationService,
                            ProfileRepository profileRepository,
                            UserRepository userRepository,
                            EventParticipantsRepository eventParticipantsRepository,
                            ApplicationRepository applicationRepository,
                            ProfileEventParticipantsRepository profileEventParticipantsRepository) {
        this.eventRepository = eventRepository;
        this.emailNotificationService = emailNotificationService;
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.eventParticipantsRepository = eventParticipantsRepository;
        this.applicationRepository = applicationRepository;
        this.profileEventParticipantsRepository = profileEventParticipantsRepository;
    }

    @Override
    public Page<EventParticipantsDto> findAvailableParticipants(String query, User currentUser, Pageable pageable) {
        return eventRepository.findAvailableParticipants(query, currentUser, pageable);
    }

    @Override
    @ValidateWith(EventRequestValidator.class)
    @Transactional
    public EventDto create(EventRequest request, User currentUser) {
        Event event = EventConverter.toEntity(request);
        event.setId(null);
        EventDto newEvent = EventConverter.toDto(eventRepository.save(event));
        List<EventParticipantsDto> participants = findParticipantsFor(newEvent);
        emailNotificationService.eventCreated(newEvent, participants, currentUser);
        return newEvent;
    }

    @Override
    @ValidateWith(EventRequestValidator.class)
    @Transactional
    public EventDto update(EventRequest request, User user) {
        Event currentEvent = eventRepository.findOne(request.getId());
        eventParticipantsRepository.delete(currentEvent.getParticipants());
        currentEvent = EventConverter.toEntity(currentEvent, request);

        List<EventParticipants> currentParticipants = new ArrayList<>();
        for (EventParticipants eventParticipants : currentEvent.getParticipants()) {
            eventParticipants.setEvent(currentEvent);
            currentParticipants.add(eventParticipants);
        }

        eventRepository.save(currentEvent);
        eventParticipantsRepository.save(currentParticipants);
        currentEvent.setParticipants(currentParticipants);

        EventDto updatedEvent = EventConverter.toDto(currentEvent);
        List<EventParticipantsDto> participants = findParticipantsFor(updatedEvent);
        emailNotificationService.eventUpdated(updatedEvent, participants, user);
        return updatedEvent;
    }

    @Override
    public List<EventDto> findEvents(Long participantId, Date startDate, Date endDate) {
        return EventConverter.toDtoList(eventRepository.findAllBy(participantId, startDate, endDate));
    }

    @Override
    @ValidateWith(ApplicationEventRequestValidator.class)
    public Page<EventDto> findApplicationEvents(Long id, Pageable pageable) {
        Application application = this.applicationRepository.findOne(id);

        List<ProfileEventParticipants> profileEventParticipants = this.profileEventParticipantsRepository.findAllByApplication(application);

        if (profileEventParticipants == null || profileEventParticipants.size() == 0) {
            return new PageImpl<>(new ArrayList<>());
        }

        List<Long> eventIds = profileEventParticipants.stream().map(c -> c.getEvent().getId()).collect(Collectors.toList());
        Page<Event> events = this.eventRepository.findByIdIn(eventIds, pageable);
        return new PageImpl<>(EventConverter.toDtoList(events.getContent()), pageable, events.getTotalElements());
    }

    private List<EventParticipantsDto> findParticipantsFor(EventDto event) {
        List<EventParticipantsDto> participants = new ArrayList<>();
        if (event == null) {
            return participants;
        }

        List<Long> participantsAsUser = event.getParticipants().stream().filter(EventParticipantsDto::getIsUser).map(IdDto::getId).collect(Collectors.toList());
        if (!CollectionHelper.isEmpty(participantsAsUser)) {
            List<User> users = this.userRepository.findAllByIdInAndEnabledTrue(participantsAsUser);
            if (!CollectionHelper.isEmpty(users)) {
                participants.addAll(users.stream().map(user -> new EventParticipantsDto(user.getId(), user.getFullName(), user.getEmail(), true)).collect(Collectors.toList()));
            }
        }

        List<Long> participantsAsProfile = event.getParticipants().stream().filter(participant -> !participant.getIsUser()).map(IdDto::getId).collect(Collectors.toList());
        if (!CollectionHelper.isEmpty(participantsAsProfile)) {
            List<Profile> profiles = this.profileRepository.findAllByIdIn(participantsAsProfile);
            if (!CollectionHelper.isEmpty(profiles)) {
//                participants.addAll(profiles.stream().map(user -> new EventParticipantsDto(user.getId(), user.getFullName(), user.getEmail(), true)).collect(Collectors.toList()));
            }
        }

        return participants;
    }
}
