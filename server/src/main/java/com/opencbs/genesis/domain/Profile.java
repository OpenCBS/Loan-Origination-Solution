package com.opencbs.genesis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "profiles")
public class Profile extends BaseEntity {

    @Type(type = "com.opencbs.genesis.domain.types.UTCDateType")
    @Column(name = "created_date", nullable = false)
    @CreatedDate
    @JsonIgnoreProperties(allowGetters = true)
    private Date createdDate;

    @JsonIgnore
    @OneToMany(mappedBy = "owner" , cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ProfileFieldValue> profileFieldValue;

    public Profile(){}
    public Profile(Long id){
        setId(id);
    }

}