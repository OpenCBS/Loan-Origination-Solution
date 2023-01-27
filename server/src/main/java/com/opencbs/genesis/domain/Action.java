package com.opencbs.genesis.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Makhsut Islamov on 19.10.2016.
 */
@Data
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "actions")
public class Action extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_state_id", nullable = false)
    private State ownerState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transition_state_id", nullable = false)
    private State transitionState;

    @OneToMany(mappedBy = "action", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Activity> activities;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
