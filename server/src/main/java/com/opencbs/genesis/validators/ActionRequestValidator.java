package com.opencbs.genesis.validators;

import com.opencbs.genesis.domain.Action;
import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.domain.ApplicationFieldValue;
import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.domain.enums.SystemPermissions;
import com.opencbs.genesis.dto.requests.ActionRequest;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.helpers.ListHelper;
import com.opencbs.genesis.helpers.SystemPermissionHelper;
import com.opencbs.genesis.repositories.ApplicationRepository;
import com.opencbs.genesis.validators.helpers.ParamsHelper;
import com.opencbs.genesis.validators.helpers.PermissionValidatorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Makhsut Islamov on 31.10.2016.
 */

@Component
public class ActionRequestValidator extends BaseValidator {
    private ApplicationRepository applicationRepository;

    @Autowired
    public ActionRequestValidator(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    protected void validateInternal(Object... params) throws ApiException {
        ActionRequest entity = ParamsHelper.getAs(params, 0);
        Long applicationId = ParamsHelper.getAs(params, 1);
        User user = ParamsHelper.getAs(params, 2);

        Application application = applicationRepository.findOne(applicationId);

        Assert.notNull(application, String.format("Application with {id: %d} not found.", applicationId));

        if(!SystemPermissionHelper.hasPermission(user, SystemPermissions.EDIT_APPLICATION)) {
            PermissionValidatorHelper.validateCurrentStatePermissions(application, user);
        }

        Action action = ListHelper.findFirstOrDefault(
                application.getCurrentState().getActions(),
                x -> x.getId().equals(entity.getActionId()));

        Assert.notNull(action, "No such action on current application state.");


        List<ApplicationFieldValue> fieldValues = application.getFieldValues()
                .stream()
                .filter(w-> w.getField().getMandatory() == action.getTransitionState().getId())
                .collect(Collectors.toList());

        List<String> messages = new ArrayList<>();

        fieldValues.forEach(fieldValue -> {
            if(StringUtils.isEmpty(fieldValue.getValue())){
                messages.add(fieldValue.getField().getCaption());
            }
        });
        Assert.isTrue(CollectionUtils.isEmpty(messages), messages.toString());
    }
}