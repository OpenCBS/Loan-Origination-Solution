package com.opencbs.genesis.repositories;

import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.domain.State;
import com.opencbs.genesis.repositories.customs.ApplicationRepositoryCustom;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by Makhsut Islamov on 26.10.2016.
 */
public interface ApplicationRepository extends Repository<Application>, ApplicationRepositoryCustom {
    @Query("select case when count(a) > 0 then true else false end from Application a where a.profile.id= ?1")
    boolean existsByProfileId(Long profileId);

    List<Application> findAllByCurrentStateEqualsAndStatusChangedAtGreaterThanAndStatusChangedAtLessThan(State currentState, Date startDate,Date endDate);
}
