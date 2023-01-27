package com.opencbs.genesis.repositories;

import com.opencbs.genesis.domain.Notification;
import com.opencbs.genesis.repositories.customs.NotificationRepositoryCustom;
import org.hibernate.criterion.Criterion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * Created by Askat on 12/8/2016.
 */
public interface NotificationRepository extends Repository<Notification>, NotificationRepositoryCustom {
}
