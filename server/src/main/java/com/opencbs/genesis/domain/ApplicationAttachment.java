package com.opencbs.genesis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Created by Makhsut Islamov on 21.11.2016.
 */
@Entity
@Table(name = "application_attachments")
@EntityListeners(AuditingEntityListener.class)
public class ApplicationAttachment extends BaseAttachment{
    @ManyToOne()
    @JoinColumn(name = "application_id")
    @JsonIgnore
    private Application application;

    @ManyToOne()
    @JoinColumn(name = "created_user_id")
    @CreatedBy
    private User createdUser;


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
}
