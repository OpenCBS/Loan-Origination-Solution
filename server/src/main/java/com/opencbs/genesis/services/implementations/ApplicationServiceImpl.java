package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.converters.ApplicationConverter;
import com.opencbs.genesis.converters.StatePermissionConverter;
import com.opencbs.genesis.domain.*;
import com.opencbs.genesis.domain.enums.StateType;
import com.opencbs.genesis.domain.enums.SystemPermissions;
import com.opencbs.genesis.dto.ApplicationDto;
import com.opencbs.genesis.dto.ApplicationSimpleDto;
import com.opencbs.genesis.dto.requests.ActionRequest;
import com.opencbs.genesis.dto.requests.ApplicationEditRequest;
import com.opencbs.genesis.dto.requests.ApplicationRequest;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.exceptions.ValidationException;
import com.opencbs.genesis.helpers.ListHelper;
import com.opencbs.genesis.helpers.StateHelper;
import com.opencbs.genesis.helpers.SystemPermissionHelper;
import com.opencbs.genesis.repositories.ApplicationLogRepository;
import com.opencbs.genesis.repositories.ApplicationRepository;
import com.opencbs.genesis.repositories.WorkflowRepository;
import com.opencbs.genesis.services.ActivityService;
import com.opencbs.genesis.services.ApplicationLogService;
import com.opencbs.genesis.services.ApplicationService;
import com.opencbs.genesis.services.UserService;
import com.opencbs.genesis.services.factories.ApplicationCriterionFactory;
import com.opencbs.genesis.validators.ActionRequestValidator;
import com.opencbs.genesis.validators.ApplicationEditRequestValidator;
import com.opencbs.genesis.validators.ApplicationRequestValidator;
import com.opencbs.genesis.validators.annotations.ValidateWith;
import com.opencbs.genesis.validators.helpers.PermissionValidatorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Makhsut Islamov on 25.10.2016.
 */
@Service
public class ApplicationServiceImpl extends BaseService implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationLogRepository applicationLogRepository;
    private final WorkflowRepository workflowRepository;
    private final ActivityService activityService;
    private final ApplicationLogService applicationLogService;
    private final UserService userService;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository,
                                  ApplicationLogRepository applicationLogRepository,
                                  WorkflowRepository workflowRepository,
                                  ActivityService activityService,
                                  ApplicationLogService applicationLogService,
                                  UserService userService) {
        this.applicationRepository = applicationRepository;
        this.applicationLogRepository = applicationLogRepository;
        this.workflowRepository = workflowRepository;
        this.activityService = activityService;
        this.applicationLogService = applicationLogService;
        this.userService = userService;
    }

    @Override
    public Page<ApplicationSimpleDto> findBy(Map<String, String> filters, Pageable pageable) throws ApiException {
        Long stateResponsibleUserId = ApplicationCriterionFactory.getStateResponsibleUserId(filters);
        User user = null;
        if (stateResponsibleUserId != 0) {
            user = userService.findById(stateResponsibleUserId);
        }

        Page<Application> pageApplications = applicationRepository.findBy(
                ApplicationCriterionFactory.create(filters),
                pageable,
                user
        );
        return new PageImpl<>(
                ApplicationConverter.toListDto(pageApplications.getContent()),
                pageable,
                pageApplications.getTotalElements()
        );
    }

    @Override
    public List<ApplicationSimpleDto> getTaskList(User principal, String query) throws ValidationException {
        List<Application> applications = StringUtils.isEmpty(query)
                ? applicationRepository.findAvailableFor(principal)
                : applicationRepository.findAvailableFor(principal, query);
        return ApplicationConverter.toListDto(applications);
    }

    @Override
    public ApplicationDto findBy(Long id, User principal) throws ApiException {
        Application application = applicationRepository.findOne(id);
        Assert.notNull(application, String.format("Application with {id: %d} not found", id));
        if (!SystemPermissionHelper.hasPermission(principal, SystemPermissions.READ_APPLICATION)) {
            PermissionValidatorHelper.validateCurrentStatePermissions(application, principal);
        }
        List<ApplicationLog> applicationLogs = applicationLogRepository.findByApplicationId(id);
        ApplicationDto applicationDto = ApplicationConverter.toDto(application);
        applicationDto.setDeletable(true);
        for (ApplicationLog applicationLog : applicationLogs) {
            if (Objects.equals(applicationLog.getToState().getName(), "Loan created"))
                applicationDto.setDeletable(false);
        }
        return applicationDto;
    }

    @Override
    public List<Application> findAllByCurrentState(State state, Date startDate, Date endDate) {
        return this.applicationRepository.findAllByCurrentStateEqualsAndStatusChangedAtGreaterThanAndStatusChangedAtLessThan(state, startDate, endDate);
    }

    @Override
    public Application findOne(Long id) {
        return this.applicationRepository.findOne(id);
    }

    @Override
    @ValidateWith(ApplicationRequestValidator.class)
    @Transactional
    public ApplicationDto create(ApplicationRequest request) throws ValidationException {
        Application application = new Application();
        application.setName(request.getName());
        application.setProfile(new Profile(request.getProfileId()));
        application.setResponsibleUser(new User(request.getResponsibleUserId()));
        application.setStatusChangedAt(new Date());

        Workflow workflow = workflowRepository.findOne(request.getWorkflowId());
        application.setWorkflow(workflow);

        application.setApplicationStatePermissions(
                StatePermissionConverter.toEntityList(application, request.getStatePermissions(), workflow)
        );

        State startState = ListHelper.findFirst(workflow.getStates(), x -> x.getStateType().equals(StateType.START));
        application.setCurrentState(startState);
        applicationRepository.save(application);
        applicationLogService.create(new ApplicationLog(application, startState));

        return ApplicationConverter.toDto(application);
    }

    @Override
    @ValidateWith(ApplicationEditRequestValidator.class)
    @Transactional
    public ApplicationDto update(Long id, ApplicationEditRequest request) throws ApiException {
        Application application = applicationRepository.findOne(id);
        application.setName(request.getName());

        return ApplicationConverter.toDto(applicationRepository.save(application));
    }

    @Override
    public ApplicationDto delete(Long id, String comment) throws ApiException {
        Application application = applicationRepository.findOne(id);
        List<ApplicationLog> applicationLogs = applicationLogRepository.findByApplicationId(id);
        for (ApplicationLog applicationLog : applicationLogs){
            if (Objects.equals(applicationLog.getToState().getName(), "Loan created"))
                throw new ValidationException("Disbursed Application cannot be deleted");
        }
        application.setDeleted(true);
        application.setComment(comment);
        return ApplicationConverter.toDto(applicationRepository.save(application));
    }

    @Override
    @ValidateWith(ActionRequestValidator.class)
    @Transactional
    public boolean performAction(ActionRequest request, Long id, User principal) throws ApiException {
        Application application = applicationRepository.findOne(id);

        Action action = ListHelper.findFirst(application.getCurrentState().getActions(),
                x -> x.getId().compareTo(request.getActionId())==0);

        applicationLogService.create(new ApplicationLog(application,
                action.getOwnerState(), action.getTransitionState(), request.getNote())
        );

        application.setCurrentState(action.getTransitionState());
        application.setCompleted(StateHelper.isCompleted(action.getTransitionState()));
        application.setStatusChangedAt(new Date());
        applicationRepository.save(application);
        activityService.execute(action, action.getActivities(), application, principal);
        //TODO: Change return type
        return true;
    }
}