package com.opencbs.genesis.domain.events;

import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.domain.Profile;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Profile")
public class ProfileEventParticipants extends EventParticipants {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_id", nullable = false)
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private Application application;

    public Profile getProfile() {
        return this.profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
