package com.opencbs.genesis.repositories.implementations;

import com.opencbs.genesis.domain.NotificationHistory;
import com.opencbs.genesis.repositories.customs.NotificationHistoryRepositoryCustom;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

/**
 * Created by alopatin on 05-Jul-17.
 */
public class NotificationHistoryRepositoryImpl extends BaseRepository implements NotificationHistoryRepositoryCustom {

    protected NotificationHistoryRepositoryImpl(EntityManager entityManager) {
        super(entityManager, NotificationHistory.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<NotificationHistory> findBy(String query, Pageable pageable) {
        Criterion queryCriterion = Restrictions.or(
                Restrictions.like("title", query, MatchMode.ANYWHERE),
                Restrictions.like("recipients", query, MatchMode.ANYWHERE),
                Restrictions.like("createdBy", query, MatchMode.ANYWHERE)
        );

        Criteria criteriaCount = createCriteria();
        criteriaCount.add(queryCriterion);
        long total = (long) criteriaCount.setProjection(Projections.rowCount()).uniqueResult();

        Criteria criteria = createCriteria();
        criteria.add(queryCriterion);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        criteria.setMaxResults(pageable.getPageSize());
        criteria.addOrder(Order.asc("createdDate"));

        return new PageImpl<>(criteria.list(), pageable, total);
    }
}
