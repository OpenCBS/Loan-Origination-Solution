package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.converters.StatePermissionConverter;
import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.domain.permissions.ApplicationStatePermission;
import com.opencbs.genesis.dto.StatePermissionDto;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.repositories.ApplicationRepository;
import com.opencbs.genesis.repositories.ApplicationStatePermissionRepository;
import com.opencbs.genesis.services.ApplicationPermissionService;
import com.opencbs.genesis.validators.ApplicationStatePermissionValidator;
import com.opencbs.genesis.validators.annotations.ValidateWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Makhsut Islamov on 13.12.2016.
 */
@Service
public class ApplicationPermissionServiceImpl extends BaseService implements ApplicationPermissionService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationStatePermissionRepository applicationStatePermissionRepository;

    @Autowired
    public ApplicationPermissionServiceImpl(ApplicationRepository applicationRepository,
                                            ApplicationStatePermissionRepository applicationStatePermissionRepository) {
        this.applicationRepository = applicationRepository;
        this.applicationStatePermissionRepository = applicationStatePermissionRepository;
    }

    @Override
    public List<StatePermissionDto> findBy(Long applicationId) {
        Application application = applicationRepository.findOne(applicationId);
        Assert.notNull(application, String.format("Application with {id: %d} not found", applicationId));

        return StatePermissionConverter.toDtoList(application.getApplicationStatePermissions());
    }

    @Override
    @ValidateWith(ApplicationStatePermissionValidator.class)
    @Transactional
    public List<StatePermissionDto> recreate(Long applicationId, List<StatePermissionDto> permissions) throws ApiException {
        Application application = applicationRepository.findOne(applicationId);
        if (!CollectionUtils.isEmpty(application.getApplicationStatePermissions())) {
            applicationStatePermissionRepository.delete(application.getApplicationStatePermissions());
            application.setApplicationStatePermissions(null);
        }

        List<ApplicationStatePermission> savedPermissions = applicationStatePermissionRepository.save(
                StatePermissionConverter.toEntityList(application, permissions)
        );

        return StatePermissionConverter.toDtoList(savedPermissions);
    }
}