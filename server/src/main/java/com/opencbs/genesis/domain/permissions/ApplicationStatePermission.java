package com.opencbs.genesis.domain.permissions;

import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.domain.BaseEntity;
import com.opencbs.genesis.domain.State;

import javax.persistence.*;

/**
 * Created by Makhsut Islamov on 23.11.2016.
 */
@Entity
@Table(name = "application_state_permissions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "[type]", discriminatorType = DiscriminatorType.STRING)
public class ApplicationStatePermission extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;


    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
