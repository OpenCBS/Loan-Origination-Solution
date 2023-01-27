package com.opencbs.genesis.services;

import com.opencbs.genesis.domain.Notification;
import com.opencbs.genesis.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Askat on 12/8/2016.
 */
public interface NotificationService {
    Page<Notification> findBy(Long applicationId, Pageable pageable);

    Notification findById(Long id);

    Page<Notification> findByNotificationDate(String date, Pageable pageable);

    Page<Notification> findAll(Pageable pageable);

    Notification create(Long applicationId, Notification notification, User principal);

    Notification update(Notification notification, Long id);
}
