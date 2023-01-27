package com.opencbs.genesis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencbs.genesis.domain.enums.FieldType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * Created by Makhsut Islamov on 20.10.2016.
 */
@Data
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "workflow_fields")
public class WorkflowField extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    @JsonIgnore
    private WorkflowFieldSection section;

    @Column(name = "field_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private FieldType fieldType;

    @Column(name = "caption", nullable = false)
    private String caption;

    @Column(name = "is_unique", nullable = false)
    private boolean unique;

    @Column(name = "is_mandatory", nullable = false)
    private int mandatory;

    @Column(name = "sort_order", nullable = false)
    private int order;

    @Column(name = "extra")
    private String extra;
}
