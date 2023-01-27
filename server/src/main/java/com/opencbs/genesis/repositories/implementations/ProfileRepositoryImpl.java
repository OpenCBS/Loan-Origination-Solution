package com.opencbs.genesis.repositories.implementations;

import com.opencbs.genesis.domain.Profile;
import com.opencbs.genesis.repositories.customs.ProfileRepositoryCustom;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

/**
 * Created by Makhsut Islamov on 14.03.2017.
 */
public class ProfileRepositoryImpl extends BaseRepository implements ProfileRepositoryCustom {
    protected ProfileRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Profile.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<Profile> findBy(String query, Pageable pageable) {
        Criterion queryCriterion = Restrictions.or(
                Restrictions.like("firstName", query, MatchMode.ANYWHERE),
                Restrictions.like("lastName", query, MatchMode.ANYWHERE),
                Restrictions.like("mobilePhone", query, MatchMode.ANYWHERE),
                Restrictions.like("email", query, MatchMode.ANYWHERE)
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
