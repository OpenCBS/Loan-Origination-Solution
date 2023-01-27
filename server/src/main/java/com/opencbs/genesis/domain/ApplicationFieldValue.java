package com.opencbs.genesis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by Makhsut Islamov on 20.10.2016.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "application_field_values")
public class ApplicationFieldValue extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    @JsonIgnore
    private Application application;

    @ManyToOne()
    @JoinColumn(name = "workflow_field_id", nullable = false)
    private WorkflowField field;

    @Column(name = "value")
    private String value;
}
