package com.opencbs.genesis.domain;

import com.opencbs.genesis.domain.enums.NotificationType;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by alopatin on 05-Jul-17.
 */

@Entity
@Table(name = "notification_historys")
@EntityListeners(AuditingEntityListener.class)
public class NotificationHistory extends BaseEntity {

    @Type(type = "com.opencbs.genesis.domain.types.UTCDateType")
    @Column(name = "created_date",nullable = false)
    @CreatedDate
    private Date createdDate;

    @Column(name = "created_by",nullable = false)
    private String createdBy;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "content",nullable = false)
    private String content;

    @Column(name = "recipients",nullable = false)
    private String recipients;

    @Column(name = "notification_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private NotificationType notificationType;


    public Date getCreatedDate(){ return this.createdDate;}
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate;}

    public String getCreatedBy() {return this.createdBy;}
    public void setCreatedBy(String createdBy){ this.createdBy = createdBy;}

    public String getTitle(){return this.title;}
    public void setTitle(String title){ this.title = title;}

    public String getContent(){ return this.content;}
    public void setContent(String content){ this.content = content;}

    public String getRecipients(){return this.recipients;}
    public void setRecipients(String recipients){this.recipients = recipients;}

    public NotificationType getNotificationType(){return this.notificationType;}
    public void setNotificationType(NotificationType notificationType) {this.notificationType = notificationType;}
}
