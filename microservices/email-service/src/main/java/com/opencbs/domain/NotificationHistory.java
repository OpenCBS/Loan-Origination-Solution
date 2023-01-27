package com.opencbs.domain;

import com.opencbs.domain.enums.NotificationType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification_history")
@Data
public class NotificationHistory extends BaseEntity {

    @Column(name = "created_date",nullable = false)
    private LocalDateTime createdDate;

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
}