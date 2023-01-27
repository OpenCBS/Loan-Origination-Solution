package com.opencbs.genesis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "profile_field_sections")
public class ProfileFieldSection extends BaseEntity {

    @Column(name = "caption", nullable = false)
    private String caption;

    @Column(name = "sort_order", nullable = false)
    private int order;

    @Column(name = "code", nullable = false)
    private String code;

    @OrderBy("order asc")
    @OneToMany(mappedBy = "section", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    List<ProfileField> profileFields;

}
