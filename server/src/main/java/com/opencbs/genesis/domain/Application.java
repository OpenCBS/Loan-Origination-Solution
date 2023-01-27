package com.opencbs.genesis.domain;

import com.opencbs.genesis.domain.permissions.ApplicationStatePermission;
import com.opencbs.genesis.helpers.ListHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Makhsut Islamov on 20.10.2016.
 */
@Data
@EqualsAndHashCode(callSuper = true)

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "applications")
public class Application extends BaseEntity{
    @ManyToOne()
    @JoinColumn(name = "workflow_id", nullable = false)
    private Workflow workflow;

    @ManyToOne()
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "application", cascade = CascadeType.PERSIST)
    private List<ApplicationFieldValue> fieldValues;

    @OneToOne()
    @JoinColumn(name = "current_state_id", nullable = true)
    private State currentState;

    @OneToMany(mappedBy = "application", cascade = CascadeType.PERSIST)
    private List<ApplicationStatePermission> applicationStatePermissions;

    @ManyToOne()
    @JoinColumn(name = "responsible_user_id", nullable = false)
    private User responsibleUser;

    @Column(name = "completed", nullable = false)
    private boolean completed;

    @Type(type = "com.opencbs.genesis.domain.types.UTCDateType")
    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private Date createdDate;

    @Type(type = "com.opencbs.genesis.domain.types.UTCDateType")
    @Column(name = "status_changed_at", nullable = false)
    private Date statusChangedAt;

    @ManyToOne()
    @JoinColumn(name = "created_user_id")
    @CreatedBy
    private User createdUser;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "comment", nullable = true)
    private String comment;

    public Application(){}
    public Application(Long id){
        setId(id);
    }

    public List<ApplicationStatePermission> getCurrentStatePermissions(){
        return ListHelper.find(getApplicationStatePermissions(),
                item -> Objects.equals(item.getState().getId(), getCurrentState().getId()));
    }


}