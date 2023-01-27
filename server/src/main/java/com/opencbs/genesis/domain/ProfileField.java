package com.opencbs.genesis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencbs.genesis.domain.enums.FieldType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "profile_fields")
public class ProfileField extends BaseEntity {

    @Column(name = "caption", nullable = false)
    private String caption;

    @Column(name = "extra")
    private ProfileFieldExtra extra;

    @Column(name = "field_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private FieldType fieldType;

    @Column(name = "is_mandatory", nullable = false)
    private boolean mandatory;

    @Column(name = "sort_order", nullable = false)
    private int order;

    @Column(name = "is_unique", nullable = false)
    private boolean unique;

    @Column(name = "code" , nullable = false, unique = true)
    private String code;

    @Column(name = "section_code" , nullable = false)
    private String sectionCode;

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    @JsonIgnore
    private ProfileFieldSection section;
}
