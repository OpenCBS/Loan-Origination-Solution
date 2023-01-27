package com.opencbs.genesis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Makhsut Islamov 11.11.2016.
 */
@Entity
@Table(name = "application_logs")
@EntityListeners(AuditingEntityListener.class)
public class ApplicationLog extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    @JsonIgnore
    private Application application;

    @ManyToOne()
    @JoinColumn(name = "from_state_id", nullable = true)
    private State fromState;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_state_id", nullable = false)
    private State toState;

    @Column(name = "note")
    private String note;

    @Type(type = "com.opencbs.genesis.domain.types.UTCDateType")
    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private Date createDate;

    @ManyToOne()
    @JoinColumn(name = "created_user_id", nullable = false)
    @CreatedBy
    private User createdUser;

    public ApplicationLog(){}
    public ApplicationLog(Application application, State toState){
        this(application, null, toState, null);
    }

    public ApplicationLog(Application application, State fromState, State toState, String note){
        this.application = application;
        this.fromState= fromState;
        this.toState = toState;
        this.note = note;
    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(User createdUser) {
        this.createdUser = createdUser;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public State getFromState() {
        return fromState;
    }

    public void setFromState(State fromState) {
        this.fromState = fromState;
    }

    public State getToState() {
        return toState;
    }

    public void setToState(State toState) {
        this.toState = toState;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}