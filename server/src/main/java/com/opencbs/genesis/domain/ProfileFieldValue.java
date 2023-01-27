package com.opencbs.genesis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "profile_field_values")
public class ProfileFieldValue extends BaseEntity{

    @Column(name = "section_code")
    private String sectionCode;

    @ManyToOne()
    @JoinColumn(name = "owner_id", nullable = false)
    private Profile owner;

    @ManyToOne()
    @JoinColumn(name = "field_id", nullable = false)
    private ProfileField field;

    @Column(name = "value")
    private String value;
}
