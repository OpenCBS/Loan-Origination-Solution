package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.domain.ApplicationLog;
import com.opencbs.genesis.dto.ApplicationLogDto;
import com.opencbs.genesis.repositories.ApplicationLogRepository;
import com.opencbs.genesis.services.ApplicationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Makhsut Islamov on 11.11.2016.
 */
@Service
public class ApplicationLogServiceImpl implements ApplicationLogService {
    private final ApplicationLogRepository applicationLogRepository;

    @Autowired
    public ApplicationLogServiceImpl(ApplicationLogRepository applicationLogRepository) {
        this.applicationLogRepository = applicationLogRepository;
    }

    @Override
    public ApplicationLog create(ApplicationLog applicationLog) {
        applicationLog.setId(0L);
        return applicationLogRepository.save(applicationLog);
    }

    @Override
    public List<ApplicationLogDto> findAllOf(Long applicationId) {
        return toDtoList(applicationLogRepository.findByApplicationId(applicationId));

    }

    private List<ApplicationLogDto> toDtoList(List<ApplicationLog> applicationLogs){
        List<ApplicationLogDto> dtoList = new ArrayList<>();
        applicationLogs.forEach(log -> dtoList.add(toDto(log)));
        return dtoList;
    }

    private ApplicationLogDto toDto(ApplicationLog applicationLog){
        ApplicationLogDto dto = new ApplicationLogDto();
        dto.setFromStateName(applicationLog.getFromState() == null ? null : applicationLog.getFromState().getName());
        dto.setToStateName(applicationLog.getToState().getName());
        dto.setNote(applicationLog.getNote());
        dto.setCreateDate(applicationLog.getCreateDate());
        dto.setCreatedUser(applicationLog.getCreatedUser());

        return dto;
    }
}