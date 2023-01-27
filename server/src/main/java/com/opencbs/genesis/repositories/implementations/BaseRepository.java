package com.opencbs.genesis.repositories.implementations;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;

/**
 * Created by Makhsut Islamov on 27.10.2016.
 */
 public abstract class BaseRepository {
    private EntityManager entityManager;
    private Class<?> entityClass;

    protected BaseRepository(EntityManager entityManager, Class<?> entityClass){
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    protected Criteria createCriteria() {
        return getSession().createCriteria(entityClass);
    }

    protected Criteria createDeletedFalseCriteria() {
        return createCriteria().add(Restrictions.eq("deleted", false));
    }

    protected Criteria createCriteria(String alias) {
        return getSession().createCriteria(entityClass, alias);
    }

    protected Criteria createCriteria(Criterion criterion) {
        Criteria criteria =  getSession().createCriteria(entityClass);
        criteria.add(criterion);

        return criteria;
    }

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    public EntityManager getEntityManager() {return this.entityManager;}
}
