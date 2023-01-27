package com.opencbs.genesis.repositories.implementations;

import com.opencbs.genesis.domain.Notification;
import com.opencbs.genesis.repositories.customs.NotificationRepositoryCustom;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

/**
 * Created by Askat on 12/14/2016.
 */
public class NotificationRepositoryImpl extends BaseRepository implements NotificationRepositoryCustom {
    protected NotificationRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Notification.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<Notification> findBy(Criterion criterion, Pageable pageable) {
        Criteria criteriaCount = createCriteria("n");
        if (criterion != null) {
            criteriaCount.add(criterion);
        }
        long total = (long) criteriaCount.setProjection(Projections.rowCount()).uniqueResult();

        Criteria criteria = createCriteria("n");
        if (criterion != null) {
            criteria.add(criterion);
        }

        criteria.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        criteria.setMaxResults(pageable.getPageSize());
        criteria.addOrder(Order.asc("n.createdDate"));
        return new PageImpl<>(criteria.list(), pageable, total);
    }
}
