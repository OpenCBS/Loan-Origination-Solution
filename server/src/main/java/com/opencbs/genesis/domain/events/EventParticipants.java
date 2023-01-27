package com.opencbs.genesis.domain.events;

import com.opencbs.genesis.domain.BaseEntity;
import com.opencbs.genesis.domain.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * Created by alopatin on 19-Apr-17.
 */
@Data
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "event_participants")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "[type]", discriminatorType = DiscriminatorType.STRING)
public class EventParticipants extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
}
