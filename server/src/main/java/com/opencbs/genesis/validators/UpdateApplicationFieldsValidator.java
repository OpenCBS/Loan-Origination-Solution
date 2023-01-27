package com.opencbs.genesis.validators;

import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.domain.WorkflowField;
import com.opencbs.genesis.domain.WorkflowFieldSection;
import com.opencbs.genesis.repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Makhsut Islamov on 06.12.2016.
 */
@Component
public class UpdateApplicationFieldsValidator extends UpdateFieldsValidator {
    @Autowired
    UpdateApplicationFieldsValidator(ApplicationRepository applicationRepository) {
        super(applicationRepository);
    }

    @Override
    protected List<WorkflowField> getWorkflowFields(Application application) {
        return application.getWorkflow().getSections().stream()
                .map(WorkflowFieldSection::getFields).flatMap(List::stream).collect(Collectors.toList());
    }
}
