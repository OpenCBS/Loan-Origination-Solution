package com.opencbs.genesis.repositories.customs;

import com.opencbs.genesis.domain.NotificationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by alopatin on 05-Jul-17.
 */
public interface NotificationHistoryRepositoryCustom {
    Page<NotificationHistory> findBy(String query, Pageable pageable);
}
