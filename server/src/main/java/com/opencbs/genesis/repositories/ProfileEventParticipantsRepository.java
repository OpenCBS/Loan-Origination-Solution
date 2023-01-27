package com.opencbs.genesis.repositories;

import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.domain.Profile;
import com.opencbs.genesis.domain.events.ProfileEventParticipants;

import java.util.List;

public interface ProfileEventParticipantsRepository extends Repository<ProfileEventParticipants> {
    List<ProfileEventParticipants> findAllByApplication(Application application);
}
