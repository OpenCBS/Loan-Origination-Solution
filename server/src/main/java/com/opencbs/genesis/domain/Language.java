package com.opencbs.genesis.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "main_language")
public class Language extends BaseEntity{

    @Column(name = "name", nullable = false)
    private String name;
}
