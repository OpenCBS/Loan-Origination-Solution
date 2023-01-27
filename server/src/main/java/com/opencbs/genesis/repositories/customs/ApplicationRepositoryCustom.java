package com.opencbs.genesis.repositories.customs;

import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.domain.User;
import org.hibernate.criterion.Criterion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Makhsut Islamov on 27.10.2016.
 */
public interface ApplicationRepositoryCustom {
    List<Application> findAvailableFor(User user);
    List findAvailableFor(User user, String query);
    Page<Application> findBy(Criterion criterion, Pageable pageable);
    Page<Application> findBy(Criterion criterion, Pageable pageable, User stateResponsibleUserId);
}
