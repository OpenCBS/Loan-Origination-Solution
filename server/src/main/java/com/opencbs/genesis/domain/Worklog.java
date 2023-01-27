package com.opencbs.genesis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Makhsut Islamov on 21.11.2016.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "work_logs")
public class Worklog extends BaseEntity {
    @ManyToOne()
    @JoinColumn(name = "application_id")
    @JsonIgnore
    private Application application;

    @Column(name = "spent_hours", nullable = false)
    private double spentHours;

    @Type(type = "com.opencbs.genesis.domain.types.UTCDateType")
    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private Date createdDate;

    @ManyToOne()
    @JoinColumn(name = "created_user_id")
    @CreatedBy
    private User createdUser;

    @Column(name = "note", nullable = false)
    private String note;

    @Type(type = "com.opencbs.genesis.domain.types.UTCDateType")
    @Column(name = "entered_date", nullable = false)
    private Date enteredDate;

    public double getSpentHours() {
        return spentHours;
    }
    public void setSpentHours(double spentHours) {
        this.spentHours = spentHours;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getCreatedUser() {
        return createdUser;
    }
    public void setCreatedUser(User createdUser) {
        this.createdUser = createdUser;
    }

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }

    public Application getApplication() {
        return application;
    }
    public void setApplication(Application application) {
        this.application = application;
    }

    public Date getEnteredDate() {
        return enteredDate;
    }
    public void setEnteredDate(Date enteredDate) {
        this.enteredDate = enteredDate;
    }
}
