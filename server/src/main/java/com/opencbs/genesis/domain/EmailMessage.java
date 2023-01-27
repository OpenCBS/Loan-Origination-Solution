package com.opencbs.genesis.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "email_messages")
public class EmailMessage extends BaseEntity{

    @Column(name = "subject")
    private String subject;

    @Column(name = "content")
    private String content;

    @Column(name = "emails")
    private String emails;

    @Column(name = "send_at")
    @Type(type = "com.opencbs.genesis.domain.types.UTCDateType")
    private Date sendAt;

    @Column(name = "is_sent")
    private boolean isSent;

    @Column(name = "event_id")
    private Long eventId;
}
