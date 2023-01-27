package com.opencbs.genesis.repositories.implementations;

import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.domain.permissions.RoleApplicationStatePermission;
import com.opencbs.genesis.domain.permissions.UserApplicationStatePermission;
import com.opencbs.genesis.repositories.customs.ApplicationRepositoryCustom;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Makhsut Islamov on 27.10.2016.
 */
public class ApplicationRepositoryImpl extends BaseRepository implements ApplicationRepositoryCustom {
    public ApplicationRepositoryImpl(EntityManager manager){
        super(manager, Application.class);
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<Application> findAvailableFor(User user) {
        return createCriteriaFindAvailableFor(user).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Application> findAvailableFor(User user, String query) {
        Criteria criteria = createCriteriaFindAvailableFor(user);
        criteria.add(Restrictions.or(
                Restrictions.like("app.name", query, MatchMode.ANYWHERE),
                Restrictions.like("profile.firstName", query, MatchMode.ANYWHERE),
                Restrictions.like("profile.lastName", query, MatchMode.ANYWHERE),
                Restrictions.like("state.name", query, MatchMode.ANYWHERE))
        );

        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<Application> findBy(Criterion criterion, Pageable pageable) {
        Criteria criteriaCount = createCriteriaInternal();
        if(criterion != null){
            criteriaCount.add(criterion);
        }
        long total = (long) criteriaCount.setProjection(Projections.rowCount()).uniqueResult();

        Criteria criteria = createCriteriaInternal();
        if(criterion != null){
            criteria.add(criterion);
        }

        criteria.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        criteria.setMaxResults(pageable.getPageSize());
        criteria.addOrder(Order.asc("app.createdDate"));
        criteria.add(Restrictions.eq("app.isDeleted", false));
        return new PageImpl<>(criteria.list(), pageable, total);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<Application> findBy(Criterion criterion, Pageable pageable, User stateResponsibleUser) {
        if (stateResponsibleUser == null) {
            return findBy(criterion, pageable);
        }

        Criteria criteriaCount = createCriteriaFindCurrentStateUser(stateResponsibleUser,criterion);
        long total = (long) criteriaCount.setProjection(Projections.rowCount()).uniqueResult();

        Criteria criteria = createCriteriaFindCurrentStateUser(stateResponsibleUser,criterion);

        criteria.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        criteria.setMaxResults(pageable.getPageSize());

        return new PageImpl<>(criteria.list(), pageable, total);
    }

    private Criteria createCriteriaFindCurrentStateUser(User user, Criterion criterion) {
        Criteria criteria = createCriteriaInternal();
        Criteria crPermissions = criteria.createCriteria("app.applicationStatePermissions", "perm",
                JoinType.LEFT_OUTER_JOIN
        );

        Criterion roleExpression = Restrictions.and(
                Restrictions.eq("class", RoleApplicationStatePermission.class),
                Restrictions.eq("perm.role.id", user.getRole().getId())
        );

        Criterion userExpression = Restrictions.and(
                Restrictions.eq("class", UserApplicationStatePermission.class),
                Restrictions.eq("perm.user.id", user.getId())
        );

        Criterion permissionExpression = Restrictions.and(
                Restrictions.or(roleExpression, userExpression),
                Restrictions.eqProperty("state.id", "perm.state.id")
        );

        crPermissions.add(permissionExpression);

        if (criterion != null) {
            criteria.add(criterion);
        }

        return criteria;
    }

    private Criteria createCriteriaFindAvailableFor(User user) {
        Criteria criteria = createCriteriaInternal();
        Criteria crPermissions = criteria.createCriteria("app.applicationStatePermissions", "perm",
                JoinType.LEFT_OUTER_JOIN
        );

        Criterion roleExpression = Restrictions.and(
                Restrictions.eq("class", RoleApplicationStatePermission.class),
                Restrictions.eq("perm.role.id", user.getRole().getId())
        );

        Criterion userExpression = Restrictions.and(
                Restrictions.eq("class", UserApplicationStatePermission.class),
                Restrictions.eq("perm.user.id", user.getId())
        );

        Criterion permissionExpression = Restrictions.and(
                Restrictions.or(roleExpression, userExpression),
                Restrictions.eqProperty("state.id","perm.state.id")
        );

        Criterion responsibleUserExpression = Restrictions.and(
                Restrictions.eq("app.responsibleUser.id", user.getId()),
                Restrictions.eq("app.completed", false)
        );
        crPermissions.add(Restrictions.or(permissionExpression, responsibleUserExpression));
        criteria.addOrder(Order.asc("app.createdDate"));


        return criteria;
    }

    private Criteria createCriteriaInternal(){
        Criteria criteria = createCriteria("app");
        criteria.createAlias("app.profile", "profile");
        criteria.createAlias("app.currentState", "state");

        criteria.createAlias("app.createdUser.role.permissions", "ignored", JoinType.NONE);

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria;
    }
}