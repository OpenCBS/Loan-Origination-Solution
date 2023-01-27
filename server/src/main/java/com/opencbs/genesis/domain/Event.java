package com.opencbs.genesis.domain;

import com.opencbs.genesis.domain.events.EventParticipants;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by alopatin on 19-Apr-17.
 */

@Entity
@Table(name = "events")
@EntityListeners(AuditingEntityListener.class)
public class Event extends BaseEntity {
    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "content",nullable = false)
    private String content;

    @Type(type = "com.opencbs.genesis.domain.types.UTCDateType")
    @Column(name = "start_date",nullable = false)
    private Date startDate;

    @Type(type = "com.opencbs.genesis.domain.types.UTCDateType")
    @Column(name = "end_date",nullable = false)
    private Date endDate;

    @Type(type = "com.opencbs.genesis.domain.types.UTCDateType")
    @Column(name = "notify_at",nullable = false)
    private Date notifyAt;

    @OneToMany(mappedBy = "event", cascade = CascadeType.PERSIST)
    private List<EventParticipants> participants;

    @Type(type = "com.opencbs.genesis.domain.types.UTCDateType")
    @Column(name = "created_date",nullable = false)
    @CreatedDate
    private Date createdDate;

    @ManyToOne()
    @JoinColumn(name = "created_user_id")
    @CreatedBy
    private User createdUser;

    @Column(name = "all_day", nullable = false)
    private boolean allDay;

    public String getTitle() { return  this.title;}
    public void setTitle(String title) {this.title = title;}

    public String getContent() { return this.content;}
    public void setContent(String content) { this.content = content;}

    public Date getStartDate() { return  this.startDate;}
    public void setStartDate(Date startDate) { this.startDate = startDate;}

    public Date getEndDate() { return this.endDate;}
    public void setEndDate(Date endDate) { this.endDate = endDate;}

    public List<EventParticipants> getParticipants() {return this.participants;}
    public void setParticipants(List<EventParticipants> participants) {this.participants = participants;}

    public Date getCreatedDate() { return this.createdDate;}
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate;}

    public User getCreatedUser() { return this.createdUser;}
    public void setCreatedUser(User createdUser) { this.createdUser = createdUser;}

    public boolean getAllDay() {
        return allDay;
    }
    public void setAllDay(boolean allDay){ this.allDay = allDay;}

    public Date getNotifyAt() {
        return notifyAt;
    }

    public void setNotifyAt(Date notifyAt) {
        this.notifyAt = notifyAt;
    }
}
