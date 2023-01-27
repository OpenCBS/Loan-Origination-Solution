package com.opencbs.genesis.domain.permissions;

import com.opencbs.genesis.domain.BaseEntity;
import com.opencbs.genesis.domain.State;

import javax.persistence.*;

/**
 * Created by Makhsut Islamov on 27.10.2016.
 */
@Entity
@Table(name = "workflow_state_permissions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "[type]", discriminatorType = DiscriminatorType.STRING)
public abstract class WorkflowStatePermission extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}