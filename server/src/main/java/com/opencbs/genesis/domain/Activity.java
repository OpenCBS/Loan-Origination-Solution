package com.opencbs.genesis.domain;

import com.opencbs.genesis.domain.enums.ActivityType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * Created by user on 20.10.2016.
 */
@Data
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "activitys")
public class Activity extends BaseEntity{
    @Column(name = "activity_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ActivityType activityType;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_id", nullable = false)
    private Action action;
}
