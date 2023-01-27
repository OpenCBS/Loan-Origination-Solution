package com.opencbs.genesis.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "country")
public class Country extends  BaseEntity{

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "iso3", nullable = false)
    private String iso3;
}
