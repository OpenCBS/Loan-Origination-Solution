package com.opencbs.genesis.validators;

import com.opencbs.genesis.domain.*;
import com.opencbs.genesis.exceptions.ForbiddenException;
import com.opencbs.genesis.repositories.ApplicationRepository;
import com.opencbs.genesis.repositories.StateFieldRepository;
import com.opencbs.genesis.validators.helpers.ParamsHelper;
import com.opencbs.genesis.validators.helpers.PermissionValidatorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Makhsut Islamov on 06.12.2016.
 */
@Component
public class UpdateCurrentStateFieldsValidator extends UpdateFieldsValidator {
    private final StateFieldRepository stateFieldRepository;

    @Autowired
    public UpdateCurrentStateFieldsValidator(ApplicationRepository applicationRepository,
                                             StateFieldRepository stateFieldRepository) {
        super(applicationRepository);
        this.stateFieldRepository = stateFieldRepository;

    }

    @Override
    protected List<WorkflowField> getWorkflowFields(Application application) {
        return getStateFields(application.getCurrentState());
    }

    @Override
    protected void validateMore(Application application, Object... params) throws ForbiddenException {
        User user = ParamsHelper.getAs(params, 2);
        PermissionValidatorHelper.validateCurrentStatePermissions(application, user);
    }

    private List<WorkflowField> getStateFields(State state){
        List<StateField> currentStateFields = stateFieldRepository.findByStateId(state.getId());

        if (CollectionUtils.isEmpty(currentStateFields)) return new ArrayList<>();
        return currentStateFields.stream().map(StateField::getField).collect(Collectors.toList());
    }
}