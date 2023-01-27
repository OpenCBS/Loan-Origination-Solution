package com.opencbs.genesis.validators;

import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.domain.WorkflowField;
import com.opencbs.genesis.dto.requests.FieldValue;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.helpers.ListHelper;
import com.opencbs.genesis.repositories.ApplicationRepository;
import com.opencbs.genesis.validators.helpers.ParamsHelper;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Makhsut Islamov on 06.12.2016.
 */
abstract class UpdateFieldsValidator extends BaseValidator {
    private final ApplicationRepository applicationRepository;

    UpdateFieldsValidator(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    protected void validateInternal(Object... params) throws ApiException {
        Long applicationId = ParamsHelper.getAs(params, 0);
        List<FieldValue> fieldValues  = ParamsHelper.getAs(params, 1);

        Application application = applicationRepository.findOne(applicationId);
        Assert.notNull(application, String.format("Application with {id: %d} not found.", applicationId));

        validateMore(application, params);
        validateFields(getWorkflowFields(application), fieldValues);
    }

    protected abstract List<WorkflowField> getWorkflowFields(Application application);

    protected void validateMore(Application application, Object... params) throws ApiException {
    }


    private void validateFields(List<WorkflowField> workflowFields, List<FieldValue> newFieldValues) {
        Assert.isTrue(CollectionUtils.isEmpty(workflowFields) || !CollectionUtils.isEmpty(newFieldValues),
                "valueFields is required field");

        if (CollectionUtils.isEmpty(workflowFields))
            return;

        Assert.isTrue(newFieldValues.size() == workflowFields.size(),
                "One of the workflow field is not provided or provided more fields than expected.");

        newFieldValues.forEach(fieldValue -> {
            WorkflowField workflowField = ListHelper.findFirstOrDefault(workflowFields,
                    x -> x.getId().equals(fieldValue.getFieldId()));

            Assert.notNull(workflowField, "Workflow field with {id: " + fieldValue.getFieldId() + "} not found.");
            //TODO: check for uniqueness
        });
    }
}
