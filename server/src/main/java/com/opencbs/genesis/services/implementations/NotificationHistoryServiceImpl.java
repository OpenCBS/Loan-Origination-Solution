package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.domain.NotificationHistory;
import com.opencbs.genesis.repositories.NotificationHistoryRepository;
import com.opencbs.genesis.services.NotificationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by alopatin on 05-Jul-17.
 */

@Service
public class NotificationHistoryServiceImpl extends BaseService implements NotificationHistoryService {
    private final NotificationHistoryRepository notificationHistoryRepository;

    @Autowired
    public NotificationHistoryServiceImpl(NotificationHistoryRepository notificationHistoryRepository){
        this.notificationHistoryRepository = notificationHistoryRepository;
    }

    @Override
    public Page<NotificationHistory> findBy(String query, Pageable pageable) {
        return StringUtils.isEmpty(query) ? findAll(pageable) : this.notificationHistoryRepository.findBy(query, pageable);
    }

    @Override
    public Page<NotificationHistory> findAll(Pageable pageable) {
        return this.notificationHistoryRepository.findAll(pageable);
    }

    @Override
    public NotificationHistory save(NotificationHistory notificationHistory) {
        return this.notificationHistoryRepository.save(notificationHistory);
    }
}
