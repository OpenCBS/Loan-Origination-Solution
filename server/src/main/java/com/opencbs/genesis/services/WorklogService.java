package com.opencbs.genesis.services;

import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.domain.Worklog;
import com.opencbs.genesis.dto.requests.WorklogRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Makhsut Islamov on 21.11.2016.
 */
public interface WorklogService {
    Page<Worklog> findBy(Long applicationId, Pageable pageable);
    Worklog create(Long applicationId, WorklogRequest worklog, User principal);
    Worklog edit(Long applicationId, Long worklogId, WorklogRequest worklog, User principal);
}
