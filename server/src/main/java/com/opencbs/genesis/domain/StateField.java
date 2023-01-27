package com.opencbs.genesis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * Created by Makhsut Islamov on 05.12.2016.
 */
@Data
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "state_fields")
public class StateField extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id", nullable = false)
    @JsonIgnore
    private State state;

    @ManyToOne()
    @JoinColumn(name = "field_id", nullable = false)
    private WorkflowField field;

    @Column(name = "is_read_only", nullable = false)
    private boolean readOnly;
}
