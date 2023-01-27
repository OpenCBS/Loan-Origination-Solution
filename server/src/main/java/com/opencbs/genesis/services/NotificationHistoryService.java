package com.opencbs.genesis.services;

import com.opencbs.genesis.domain.NotificationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by alopatin on 05-Jul-17.
 */
public interface NotificationHistoryService {
    Page<NotificationHistory> findBy(String query, Pageable pageable);
    Page<NotificationHistory> findAll(Pageable pageable);
    NotificationHistory save(NotificationHistory notificationHistory);
}
