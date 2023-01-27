package com.opencbs.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "email_message")
@Data
public class Message  extends BaseEntity{

    @Column(name = "subject")
    private String subject;

    @Column(name = "content")
    private String content;

    @Column(name = "emails")
    private String emails;

    @Column(name = "send_at")
    private LocalDateTime sendAt;

    @Column(name = "is_sent")
    private boolean isSent;
}
