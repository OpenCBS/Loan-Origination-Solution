package com.opencbs.genesis.services;

import com.opencbs.genesis.domain.ApplicationLog;
import com.opencbs.genesis.dto.ApplicationLogDto;

import java.util.List;

/**
 * Created by Makhsut Islamov on 11.11.2016.
 */
public interface ApplicationLogService {
    ApplicationLog create(ApplicationLog applicationLog);
    List<ApplicationLogDto> findAllOf(Long applicationId);
}
