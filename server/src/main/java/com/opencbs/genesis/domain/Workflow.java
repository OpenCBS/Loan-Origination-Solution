package com.opencbs.genesis.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by Makhsut Islamov on 19.10.2016.
 */
@Data
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "workflows")
public class Workflow  extends BaseEntity{
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "workflow")
    @JsonIgnore
    private List<State> states;

    @OneToMany(mappedBy = "workflow")
    @JsonIgnore
    private List<WorkflowFieldSection> sections;

    @Column(name="isDefault", nullable = false)
    private boolean  isDefault;
}
