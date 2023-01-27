package com.opencbs.genesis.domain;

import com.opencbs.genesis.domain.enums.StateType;
import com.opencbs.genesis.domain.permissions.WorkflowStatePermission;
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
@Table(name = "states")
public class State extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id", nullable = false)
    private Workflow workflow;

    @OneToMany(mappedBy = "ownerState", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Action> actions;

    @Column(name = "state_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private StateType stateType;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "state")
    private List<WorkflowStatePermission> permissions;

    public State(){}
    public State(Long id){
        setId(id);
    }
}
