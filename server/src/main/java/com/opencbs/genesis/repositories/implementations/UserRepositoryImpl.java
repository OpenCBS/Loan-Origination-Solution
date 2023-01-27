package com.opencbs.genesis.repositories.implementations;

import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.PermissionDto;
import com.opencbs.genesis.repositories.customs.UserRepositoryCustom;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alopatin on 12-Apr-17.
 */
public class UserRepositoryImpl extends BaseRepository implements UserRepositoryCustom {
    protected UserRepositoryImpl(EntityManager entityManager) {super(entityManager, User.class);}

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findAll(List<PermissionDto> permissions) {
        Criteria criteria = createCriteriaInternal();

        List<Long> rolesIdList = permissions.stream().filter(PermissionDto::isRole).map(PermissionDto::getRoleId).collect(Collectors.toList());
        Criterion rolesIdCriterion = Restrictions.in("role.id",rolesIdList);

        List<Long> usersIdList = permissions.stream().filter(perm-> !perm.isRole()).map(PermissionDto::getUserId).collect(Collectors.toList());
        Criterion usersIdCriterion =  Restrictions.in("user.id",usersIdList);

        if(usersIdList.size() !=0 && rolesIdList.size() !=0){
            Criterion userOrRoleCriterion = Restrictions.or(rolesIdCriterion, usersIdCriterion);
            criteria.add(userOrRoleCriterion);
        }
        else if(usersIdList.size()==0){
            criteria.add(rolesIdCriterion);
        }else{
            criteria.add(usersIdCriterion);
        }
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<User> findBy(String query, Pageable pageable) {
        Criterion queryCriterion = Restrictions.or(
                Restrictions.like("firstName", query, MatchMode.ANYWHERE),
                Restrictions.like("lastName", query, MatchMode.ANYWHERE),
                Restrictions.like("username", query, MatchMode.ANYWHERE)
        );

        Criteria criteriaCount = createCriteria();
        criteriaCount.add(queryCriterion);
        long total = (long) criteriaCount.setProjection(Projections.rowCount()).uniqueResult();

        Criteria criteria = createCriteria();
        criteria.add(queryCriterion);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        criteria.setMaxResults(pageable.getPageSize());
        criteria.addOrder(Order.asc("username"));

        return new PageImpl<>(criteria.list(), pageable, total);
    }

    private Criteria createCriteriaInternal(){
        Criteria criteria = createCriteria("user");
        criteria.createAlias("user.role","role");
        return criteria;
    }
}
