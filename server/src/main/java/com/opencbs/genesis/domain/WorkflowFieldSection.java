package com.opencbs.genesis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Makhsut Islamov on 20.10.2016.
 */
@Data
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "workflow_field_sections")
public class WorkflowFieldSection extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id", nullable = false)
    @JsonIgnore
    private Workflow workflow;

    @Column(name = "caption", nullable = false)
    private String caption;

    @Column(name = "sort_order", nullable = false)
    private Integer order;

    @OrderBy("order asc")
    @OneToMany(mappedBy = "section", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<WorkflowField> fields;
}
