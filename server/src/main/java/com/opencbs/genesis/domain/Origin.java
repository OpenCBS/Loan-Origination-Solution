package com.opencbs.genesis.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "origin")
public class Origin extends BaseEntity{

    @Column(name = "name", nullable = false)
    private String name;
}
