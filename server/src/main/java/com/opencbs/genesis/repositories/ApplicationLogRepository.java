package com.opencbs.genesis.repositories;

import com.opencbs.genesis.domain.ApplicationLog;

import java.util.List;

/**
 * Created by Makhsut Islamov on 11.11.2016.
 */
public interface ApplicationLogRepository extends Repository<ApplicationLog> {
    List<ApplicationLog> findByApplicationId(Long applicationId);
}
