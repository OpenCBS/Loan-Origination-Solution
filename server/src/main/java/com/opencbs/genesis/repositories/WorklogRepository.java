package com.opencbs.genesis.repositories;

import com.opencbs.genesis.domain.Worklog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Makhsut Islamov on 21.11.2016.
 */
public interface WorklogRepository extends Repository<Worklog> {
    Page<Worklog> findByApplicationId(Long applicationId, Pageable pageable);
}
