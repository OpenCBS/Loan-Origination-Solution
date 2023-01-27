package com.opencbs.genesis.repositories.customs;

import com.opencbs.genesis.domain.Notification;
import org.hibernate.criterion.Criterion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Askat on 12/14/2016.
 */
public interface NotificationRepositoryCustom {
    Page<Notification> findBy(Criterion criterion, Pageable pageable);
}
