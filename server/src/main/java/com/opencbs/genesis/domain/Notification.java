package com.opencbs.genesis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Askat on 12/8/2016.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "notifications")
public class Notification extends BaseEntity {
    @ManyToOne()
    @JoinColumn(name = "application_id")
    @JsonIgnore
    private Application application;

    @ManyToOne()
    @JoinColumn(name = "created_user_id")
    @CreatedBy
    private User cretedUser;

    @Type(type = "com.opencbs.genesis.domain.types.UTCDateType")
    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private Date createdDate;

    @Type(type = "com.opencbs.genesis.domain.types.UTCDateType")
    @Column(name = "notification_date", nullable = false)
    private Date notificationDate;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "done", nullable = false)
    private boolean done;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public User getCretedUser() {
        return cretedUser;
    }

    public void setCretedUser(User cretedUser) {
        this.cretedUser = cretedUser;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}