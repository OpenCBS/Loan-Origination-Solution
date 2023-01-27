package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.converters.WorklogConverter;
import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.domain.Worklog;
import com.opencbs.genesis.dto.requests.WorklogRequest;
import com.opencbs.genesis.repositories.WorklogRepository;
import com.opencbs.genesis.services.ApplicationService;
import com.opencbs.genesis.services.EmailNotificationService;
import com.opencbs.genesis.services.WorklogService;
import com.opencbs.genesis.validators.WorklogValidator;
import com.opencbs.genesis.validators.annotations.ValidateWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Makhsut Islamov on 21.11.2016.
 */
@Service
public class WorklogServiceImpl extends BaseService implements WorklogService{
    private final WorklogRepository worklogRepository;
    private final ApplicationService applicationService;
    private final EmailNotificationService emailNotificationService;

    @Autowired
    public WorklogServiceImpl(WorklogRepository worklogRepository,
                              ApplicationService applicationService,
                              EmailNotificationService emailNotificationService) {
        this.worklogRepository = worklogRepository;
        this.applicationService = applicationService;
        this.emailNotificationService = emailNotificationService;
    }

    @Override
    public Page<Worklog> findBy(Long applicationId, Pageable pageable) {
        return worklogRepository.findByApplicationId(applicationId, pageable);
    }

    @Override
    @ValidateWith(WorklogValidator.class)
    @Transactional
    public Worklog create(Long applicationId, WorklogRequest worklog, User principal) {
        Application application = this.applicationService.findOne(applicationId);

        Worklog entity = WorklogConverter.toEntity(worklog);
        entity.setId(null);
        entity.setApplication(application);
        entity = worklogRepository.save(entity);

        this.emailNotificationService.worklogAdded(entity);

        return entity;
    }

    public Worklog edit(Long applicationId, Long worklogId, WorklogRequest worklog, User principal) {
        Worklog worklogEntity = this.worklogRepository.findOne(worklogId);

        worklogEntity.setNote(worklog.getNote());
        worklogEntity.setEnteredDate(worklog.getEnteredDate());
        worklogEntity.setSpentHours(worklog.getSpentHours());
        this.worklogRepository.save(worklogEntity);

        this.emailNotificationService.worklogAdded(worklogEntity);

        return worklogEntity;
    }
}
